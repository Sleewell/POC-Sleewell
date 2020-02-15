package com.example.sleewell.ui.alarms

import android.annotation.TargetApi
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.sleewell.R

class NotificationHelper(base: Context?) : ContextWrapper(base) {
    private var mManager: NotificationManager? = null
    @TargetApi(Build.VERSION_CODES.O)
    private fun createChannel() {
        val channel = NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH)
        manager!!.createNotificationChannel(channel)
    }

    val manager: NotificationManager?
        get() {
            if (mManager == null) {
                mManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            }
            return mManager
        }

    val channelNotification: NotificationCompat.Builder
        get() {
            val intent = Intent(this, AlarmsViewModel::class.java)
            val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
            return NotificationCompat.Builder(applicationContext, channelID)
                    .setContentTitle("Sleewell")
                    .setContentText("It's time to wake up !")
                    .setSmallIcon(R.drawable.logo_sleewell)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
        }

    companion object {
        const val channelID = "channelID"
        const val channelName = "Channel Name"
    }

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel()
        }
    }
}