package com.example.sleewell.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.preference.PreferenceManager
import com.tomerrosenfeld.customanalogclockview.CustomAnalogClock


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(com.example.sleewell.R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(com.example.sleewell.R.id.text_home)

        // How to get preferences
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        if (prefs.all["SleepMode"] == true) {
            textView.text = "Sleep ON"
        } else {
            textView.text = "Sleep OFF"
        }

        val customAnalogClock = root.findViewById(com.example.sleewell.R.id.analog_clock) as CustomAnalogClock
        customAnalogClock.init(context)
        customAnalogClock.setAutoUpdate(true)

        return root
    }
}