package com.example.sleewell.ui.sounds

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.sleewell.R

class SoundsFragment : Fragment() {

    private lateinit var soundsViewModel: SoundsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        soundsViewModel =
            ViewModelProviders.of(this).get(SoundsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_sounds, container, false)
        val textView: TextView = root.findViewById(R.id.text_sounds)
        soundsViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}