package com.example.sleewell.ui.home

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.nfc.NfcAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.sleewell.R
import com.tomerrosenfeld.customanalogclockview.CustomAnalogClock
import kotlinx.android.synthetic.main.fragment_home.*

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
        val customAnalogClock = root.findViewById(R.id.analog_clock) as CustomAnalogClock
        customAnalogClock.init(context)
        customAnalogClock.setAutoUpdate(true)

        val ani = root.background as AnimationDrawable
        ani.setEnterFadeDuration(10)
        ani.setExitFadeDuration(5000)
        ani.start()

        return root
    }
}