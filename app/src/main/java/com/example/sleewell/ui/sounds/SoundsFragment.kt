package com.example.sleewell.ui.sounds

import android.app.PendingIntent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.preference.PreferenceFragmentCompat
import com.example.sleewell.R

class SoundsFragment : Fragment() {

    private lateinit var soundsViewModel: SoundsViewModel
    private  lateinit var listView: ListView
    private lateinit var list: MutableList<String>
    private lateinit var adapter: ListAdapter
    private var mediaPlayer: MediaPlayer? = null
    companion object {
        var music_select = 0
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        soundsViewModel = ViewModelProviders.of(this).get(SoundsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_sounds, container, false)

        listView = root.findViewById(R.id.music_list_view) as ListView
        list = ArrayList()

        val fields = R.raw::class.java.fields
        for (i in fields.indices) {
            list.add(fields[i].name)
        }
        adapter = ArrayAdapter(root.context, android.R.layout.simple_list_item_1, list)
        listView.adapter = adapter

        listView.onItemClickListener = AdapterView.OnItemClickListener{ _, _, i, _ ->
            if (mediaPlayer != null) {
                mediaPlayer!!.release()
            }
            val singh = resources.getIdentifier(list[i], "raw", root.context.packageName)
            mediaPlayer = MediaPlayer.create(root.context, singh)
            mediaPlayer!!.start()
            music_select = i
        }
        return root
    }

    override fun onDestroy() {
        mediaPlayer?.stop()
        super.onDestroy()
    }
}