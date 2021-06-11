package com.sd.nykaa_library.services

import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.*
import androidx.lifecycle.MutableLiveData
import com.sd.nykaa_library.constants.AppConstants
import com.sd.nykaa_library.data.database.entity.Sessions
import com.sd.nykaa_library.data.repository.SessionRepository
import com.sd.nykaa_library.utils.NotificationHelper.getNotification
import com.sd.nykaa_library.utils.SingleLiveEvent
import com.sd.nykaa_library.utils.Utils
import com.sd.nykaa_library.utils.formatTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject


class SessionService : Service() {

    private val repository: SessionRepository by inject()
    private val mBinder: IBinder = MyBinder()
    private val notiId = 1000
    private var activeSession: Sessions? = null
    private var countDownTimer: CountDownTimer? = null
    val textToShow = MutableLiveData<String>()
    val sessionEnded = SingleLiveEvent<Unit>()

    override fun onCreate() {
        super.onCreate()
        startForeground()
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            if (it.action == AppConstants.START_SERVICE) {
                if (activeSession == null) {
                    checkSession(true)
                }
            } else if (it.action == AppConstants.FROM_REBOOT) {
                checkSession(false)
            } else {
                if (activeSession == null) {
                    stopService()
                } else {
                    stopSession()
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }


    private fun checkSession(needToStart: Boolean) {
        GlobalScope.launch(Dispatchers.IO) {
            activeSession = repository.getActiveSession()
            if (activeSession == null) {
                if (needToStart) {
                    startSession()
                } else {
                    stopService()
                }
            } else {
                withContext(Dispatchers.Main) {
                    startCountDown()
                }
            }
        }
    }

    private fun startCountDown() {
        val endMillis =
            ((activeSession?.assignedDuration ?: 0) * 60 * 1000) + (activeSession?.startTime ?: 0)
        val remainingMillis = endMillis - System.currentTimeMillis()
        if (remainingMillis > 0) {
            startTimer(remainingMillis)
        } else {
            sessionEnded.call()
            stopSession()
        }
    }

    private fun stopSession() {
        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                countDownTimer?.cancel()
                sessionEnded.call()
            }
            withContext(Dispatchers.IO) {
                activeSession?.endTime = System.currentTimeMillis()
                activeSession?.isActive = false
                repository.updateSession(activeSession!!)
                activeSession = null
                stopService()
            }

        }

    }

    private fun startSession() {
        GlobalScope.launch(Dispatchers.IO) {
            activeSession = Sessions(
                startTime = System.currentTimeMillis(),
                assignedDuration = Utils.getRandomInterval(),
                isActive = true
            )
            val id = repository.insertSession(activeSession!!)
            activeSession?.id = id
            withContext(Dispatchers.Main) {
                startCountDown()
            }
        }
    }

    private fun startTimer(remainingMillis: Long) {
        //  Handler(Looper.getMainLooper()).post {
        countDownTimer = object : CountDownTimer(remainingMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                textToShow.value = millisUntilFinished.formatTime()
                updateNotification("${textToShow.value!!} remaining")
            }

            override fun onFinish() {
                countDownTimer = null
                stopSession()
            }
        }
        countDownTimer?.start()
        // }
    }


    private fun updateNotification(value: String) {
        val notification = getNotification(this, value)
        val mNotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        mNotificationManager.notify(notiId, notification)
    }


    override fun onBind(intent: Intent): IBinder {
        return mBinder
    }

    private fun startForeground() {
        startForeground(notiId, getNotification(this, ""))
    }

    private fun stopService() {
        stopForeground(true)
        stopSelf()
    }


    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel()
    }


    inner class MyBinder : Binder() {
        val service: SessionService
            get() = this@SessionService
    }

}