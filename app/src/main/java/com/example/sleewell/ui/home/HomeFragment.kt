package com.example.sleewell.ui.home

import android.content.Intent
import android.nfc.NfcAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.preference.PreferenceManager
import com.example.sleewell.ProtoActivated
import com.example.sleewell.R
import com.tomerrosenfeld.customanalogclockview.CustomAnalogClock
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        // How to get preferences
        setParam(root)
        val customAnalogClock = root.findViewById(R.id.analog_clock) as CustomAnalogClock
        customAnalogClock.init(context)
        customAnalogClock.setAutoUpdate(true)
        return root
    }

    fun setParam(root : View) {
        val textView: TextView = root.findViewById(R.id.text_home)
        val textHalo: TextView = root.findViewById(R.id.halo_home)
        val textMusic: TextView = root.findViewById(R.id.music_home)
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)

        if (prefs.all["SleepMode"] == true) {
            textView.text = "Sleep ON"
        } else {
            textView.text = "Sleep OFF"
        }
        if (prefs.all["Halo"] == true) {
            textHalo.text = "Halo ON"
        } else {
            textHalo.text = "Halo OFF"
        }
        if (prefs.all["Music"] == true) {
            textMusic.text = "Music ON"
        } else {
            textMusic.text = "Music OFF"
        }
    }

    override fun onResume() {
        setParam(view!!)
        super.onResume()
    }
}