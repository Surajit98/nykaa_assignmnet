package com.sd.nykaa_library.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.sd.nykaa_library.R
import com.sd.nykaa_library.constants.AppConstants
import com.sd.nykaa_library.ui.main.MainActivity

object NotificationHelper {

    fun getNotification(context: Context, msg: String): Notification {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(
                NotificationChannel(
                    AppConstants.CHANNEL_ID,
                    AppConstants.CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
                )
            )
        }
        val contentIntent = PendingIntent.getActivity(
            context,
            0, Intent(context, MainActivity::class.java), 0
        )

        return NotificationCompat.Builder(context,  AppConstants.CHANNEL_ID)
            .setContentTitle(context.getString(R.string.app_name))
            .setContentText(msg)
            .setOnlyAlertOnce(true) // so when data is updated don't make sound and alert in android 8.0+
            .setOngoing(true)
            .setSmallIcon(R.drawable.ic_nykaa)
            .setContentIntent(contentIntent)
            .build();
    }
}