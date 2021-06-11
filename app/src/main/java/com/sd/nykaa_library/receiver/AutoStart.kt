package com.sd.nykaa_library.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import com.sd.nykaa_library.constants.AppConstants
import com.sd.nykaa_library.services.SessionService

class AutoStart : BroadcastReceiver() {

    /**
     * Listens for Android's BOOT_COMPLETED broadcast and then executes
     * the onReceive() method.
     */
    override fun onReceive(context: Context, arg1: Intent) {
        startService(context)
    }

    private fun startService(context: Context) {
        val intent = Intent(context, SessionService::class.java)
        intent.action = AppConstants.START_SERVICE
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent)
        } else {
            context.startService(intent)
        }
    }
}