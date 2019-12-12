package com.example.sleewell

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.provider.Settings
import android.view.View
import androidx.annotation.RequiresApi
import androidx.preference.PreferenceManager
import com.example.sleewell.ui.sounds.SoundsFragment
import kotlinx.android.synthetic.main.activity_proto_activated.*

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class ProtoActivated : AppCompatActivity() {
    private val mHideHandler = Handler()
    private val mHidePart2Runnable = Runnable {
        fullscreen_content.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LOW_PROFILE or
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
    }
    private val mShowPart2Runnable = Runnable {
        supportActionBar?.show()
        fullscreen_content_controls.visibility = View.VISIBLE
    }
    private var mVisible: Boolean = false
    private val mHideRunnable = Runnable { hide() }
    private val mDelayHideTouchListener = View.OnTouchListener { _, _ ->
        if (AUTO_HIDE) {
            delayedHide(AUTO_HIDE_DELAY_MILLIS)
        }
        false
    }

    var brightnessup: Boolean = true
    var brightness = 0
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var list: MutableList<String>


    private val timer = object: CountDownTimer(30000, 100) {

        override fun onTick(millisUntilFinished: Long) {
            if (brightnessup)
                brightness += 5
            else
                brightness -= 5
            if (brightness > 100)
                brightnessup = false
            else if (brightness <= 0)
                brightnessup = true
            Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, brightness)
        }
        override fun onFinish() {}
    }
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_proto_activated)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mVisible = true
        fullscreen_content.setOnClickListener { toggle() }
        dummy_button.setOnTouchListener(mDelayHideTouchListener)
        if (Settings.System.canWrite(this.baseContext)) {
            Settings.System.putInt(this.contentResolver, Settings.System.SCREEN_BRIGHTNESS, brightness)
        } else {
            val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
            intent.data = Uri.parse("package:com.example.sleewell")
            startActivity(intent)
        }

        list = ArrayList()
        val fields = R.raw::class.java.fields
        for (i in fields.indices) {
            list.add(fields[i].name)
        }
        if (mediaPlayer != null) {
            mediaPlayer!!.release()
        }
        val singh = resources.getIdentifier(list[SoundsFragment.music_select], "raw", applicationContext.packageName)
        mediaPlayer = MediaPlayer.create(applicationContext, singh)
        val prefs = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        if (prefs.all["Music"] == true) {
            mediaPlayer!!.start()
        }
        if (prefs.all["Halo"] == true)
            timer.start()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        delayedHide(100)
    }

    private fun toggle() {
        if (mVisible) {
            hide()
        } else {
            show()
        }
    }

    private fun hide() {
        supportActionBar?.hide()
        fullscreen_content_controls.visibility = View.GONE
        mVisible = false

        mHideHandler.removeCallbacks(mShowPart2Runnable)
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY.toLong())
    }

    private fun show() {
        fullscreen_content.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        mVisible = true

        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY.toLong())
    }

    private fun delayedHide(delayMillis: Int) {
        mHideHandler.removeCallbacks(mHideRunnable)
        mHideHandler.postDelayed(mHideRunnable, delayMillis.toLong())
    }

    fun back(v : View) {
        timer.cancel()
        if (mediaPlayer != null)
            mediaPlayer!!.stop()
        finish()
    }

    companion object {
        private const val AUTO_HIDE = true
        private const val AUTO_HIDE_DELAY_MILLIS = 3000
        private const val UI_ANIMATION_DELAY = 300
    }
}
