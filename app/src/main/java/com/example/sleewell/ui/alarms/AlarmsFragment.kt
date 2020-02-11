package com.example.sleewell.ui.alarms

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.sleewell.R
import java.util.*

class AlarmsFragment : Fragment() {

    private lateinit var alarmsViewModel: AlarmsViewModel
    private lateinit var timePicker: TimePicker
    private lateinit var updateText: TextView
    private lateinit var alarmManager: AlarmManager
    // private lateinit var context: Context
    private lateinit var pendingIntent: PendingIntent
    private var choose_music: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        alarmsViewModel =
            ViewModelProviders.of(this).get(AlarmsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_alarms, container, false)
        super.onCreate(savedInstanceState)
        // context = this
        alarmManager = this.context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        timePicker = root.findViewById(R.id.timePicker)
        updateText = root.findViewById(R.id.update_text)

        val calendar = Calendar.getInstance()

        val myIntent = Intent(context, AlarmReceiver::class.java)

        val spinner = root.findViewById<Spinner>(R.id.spinner)
        val adapter = ArrayAdapter.createFromResource(context!!, R.array.alarms_array, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        //spinner.onItemSelectedListener =

        val alarm_on = root.findViewById<Button>(R.id.alarm_on)
        alarm_on.setOnClickListener {
            calendar.set(Calendar.HOUR_OF_DAY, timePicker.hour)
            calendar.set(Calendar.MINUTE, timePicker.minute)

            val hour = timePicker.hour
            val minute = timePicker.minute
            val hour_string = hour.toString()
            val minute_string = minute.toString()

            setAlarmText("Alarm set to $hour_string:$minute_string")

            myIntent.putExtra("extra", "alarm on")

            myIntent.putExtra("music_choice", choose_music)

            pendingIntent = PendingIntent.getBroadcast(
                context!!,
                0,
                myIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
        }

        val alarm_off = root.findViewById<Button>(R.id.alarm_off)
        alarm_off.setOnClickListener {
            setAlarmText("Alarm off")

            val alarmUp = PendingIntent.getBroadcast(
                context, 0,
                Intent("com.my.package.MY_UNIQUE_ACTION"),
                PendingIntent.FLAG_NO_CREATE
            ) != null
            if (alarmUp)
                alarmManager.cancel(pendingIntent)

            myIntent.putExtra("extra", "alarm off")

            myIntent.putExtra("music_choice", choose_music)
            context!!.sendBroadcast(myIntent)
        }
        return root
    }

    private fun setAlarmText(output: String) {
        updateText.text = output
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }
}