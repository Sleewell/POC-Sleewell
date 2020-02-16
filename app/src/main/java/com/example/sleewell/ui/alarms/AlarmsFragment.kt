package com.example.sleewell.ui.alarms

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.sleewell.R
import java.text.DateFormat
import java.util.*
import android.widget.Button
import android.widget.TextView
import android.widget.TimePicker
import android.app.Dialog
import android.app.TimePickerDialog


class AlarmsFragment : Fragment() {
    private var mTextView: TextView? = null
    private lateinit var alarmsViewModel: AlarmsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        alarmsViewModel = ViewModelProviders.of(this).get(AlarmsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_alarms, container, false)
        super.onCreate(savedInstanceState)
        mTextView = root.findViewById(R.id.textView)
        var buttonTimePicker = root.findViewById<Button>(R.id.button_timepicker)
        buttonTimePicker.setOnClickListener {
            val timePicker: DialogFragment = TimePickerFragment()
            timePicker.show(activity!!.supportFragmentManager, "time picker")
        }
        var buttonCancelAlarm = root.findViewById<Button>(R.id.button_cancel)
        buttonCancelAlarm.setOnClickListener { cancelAlarm() }
        return root
    }

    private fun cancelAlarm() {
        val alarmManager = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlertReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 1, intent, 0)
        alarmManager.cancel(pendingIntent)
        mTextView!!.text = "Alarm canceled"
        if (AlertReceiver.mp != null)
            AlertReceiver.mp!!.stop()
    }
}