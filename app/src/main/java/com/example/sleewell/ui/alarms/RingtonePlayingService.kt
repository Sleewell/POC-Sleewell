package com.example.sleewell.ui.alarms

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

import java.util.Random

class RingtonePlayingService : Service() {

    private lateinit var mediaPlayer: MediaPlayer
    internal var isRunning: Boolean = false
    internal var start_id: Int = 0

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

        val state = intent.extras!!.getString("extra")
        val musicChoice = intent.extras!!.getInt("music_choice")

        assert(state != null)
        when (state) {
            "alarm on" -> start_id = 1
            else -> start_id = 0
        }

        if (!this.isRunning && start_id == 1) {

            isRunning = true
            start_id = 0

            assert(musicChoice != null)
            if (musicChoice == 0) {

                val min = 1
                val max = 2

                val random = Random()
                val musicNumber = random.nextInt(max + min)

                if (musicNumber == 1) {
                    val singh = resources.getIdentifier(
                        "hollow",
                        "raw",
                        this.applicationContext.packageName
                    )
                    mediaPlayer = MediaPlayer.create(this, singh)
                    mediaPlayer.start()

                } else {
                    val singh =
                        resources.getIdentifier("sonar", "raw", this.applicationContext.packageName)
                    mediaPlayer = MediaPlayer.create(this, singh)
                    mediaPlayer.start()
                }

            } else if (musicChoice == 1) {
                val singh =
                    resources.getIdentifier("hollow", "raw", this.applicationContext.packageName)
                mediaPlayer = MediaPlayer.create(this, singh)
                mediaPlayer.start()

            } else {
                val singh =
                    resources.getIdentifier("sonar", "raw", this.applicationContext.packageName)
                mediaPlayer = MediaPlayer.create(this, singh)
                mediaPlayer.start()

            }

        } else if (!this.isRunning && start_id == 0) {

            isRunning = false
            start_id = 0

        } else if (this.isRunning && start_id == 1) {

            isRunning = true
            start_id = 1

        } else {

            mediaPlayer.stop()
            mediaPlayer.reset()

            isRunning = false
            start_id = 1

        }
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        isRunning = false
    }
}