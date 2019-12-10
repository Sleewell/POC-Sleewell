package com.example.sleewell.ui.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.sleewell.R
import com.example.sleewell.ui.statistics.StatisticsViewModel

class StatisticsFragment : Fragment() {

    private lateinit var staisticsViewModel: StatisticsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        staisticsViewModel =
            ViewModelProviders.of(this).get(StatisticsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        staisticsViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}