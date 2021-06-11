package com.sd.nykaa_library.ui.main

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.IBinder
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.sd.nykaa_library.R
import com.sd.nykaa_library.constants.AppConstants
import com.sd.nykaa_library.databinding.ActivityMainBinding
import com.sd.nykaa_library.services.SessionService
import com.sd.nykaa_library.ui.base.BaseActivity
import com.sd.nykaa_library.ui.history.HistoryActivity
import com.sd.nykaa_library.utils.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : BaseActivity() {

    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding
    var mService: SessionService? = null


    override fun setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.apply {
            lifecycleOwner = this@MainActivity
            viewModel = this@MainActivity.viewModel
        }
        setSupportActionBar(binding.toolbarLayout.toolbar)
    }

    override fun init() {
        viewModel.session.observe(this, {
            viewModel.activeSession.value = it?.isActive ?: false
            if (it != null) {
                startService()
            }
        })
        viewModel.toggleSession.observe(this, {
            if (it) {
                startService()
            } else {
                stopService()
            }
        })
        viewModel.getText().observe(this,{
            viewModel.textToShow.value=it
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.historyItem -> {
                startActivity(Intent(this@MainActivity,HistoryActivity::class.java))
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun startService() {
        Intent(this, SessionService::class.java).also {
            it.action = AppConstants.START_SERVICE
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(it)
            } else {
                startService(it)
            }
        }
        lifecycleScope.launch {
            delay(500)
            bindService()
        }


    }

    private fun stopService() {
        Intent(this, SessionService::class.java).also {
            it.action = AppConstants.STOP_SERVICE
            startService(it)
        }
    }

    private fun unbindService() {
        Intent(this, SessionService::class.java).also {
            unbindService(serviceConnection)
        }
    }

    private fun bindService() {
        Intent(this, SessionService::class.java).also {
            bindService(it, serviceConnection, Context.BIND_AUTO_CREATE)
        }
    }

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, iBinder: IBinder) {
            val binder = iBinder as SessionService.MyBinder
            mService = binder.service
            viewModel.activeSession.value = true
            addServiceObservers()
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            mService = null
            log { "Service Disconnected" }
        }
    }

    private fun addServiceObservers() {
        mService?.textToShow?.observe(this, {
            viewModel.textToShow.value = it
        })
        mService?.sessionEnded?.observe(this, {
            viewModel.activeSession.value = false
            unbindService()
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService()
    }
}