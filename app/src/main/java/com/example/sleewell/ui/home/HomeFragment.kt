package com.example.sleewell.ui.home

import android.app.NotificationManager
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context.*
import android.net.wifi.WifiManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.preference.PreferenceManager
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.sleewell.SettingsActivity


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
        return root
    }
}