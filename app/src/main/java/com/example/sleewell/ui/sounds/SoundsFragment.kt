package com.example.sleewell.ui.sounds

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.preference.PreferenceManager
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote


class SoundsFragment : Fragment() {
    private val CLIENT_ID = "d28a6b2240514ac1a918765697a631a1" //TODO a changer
    private val REDIRECT_URI = "http://com.example.sleewell/callback"
    private var mSpotifyAppRemote: SpotifyAppRemote? = null


    private lateinit var soundsViewModel: SoundsViewModel
    private  lateinit var listView: ListView
    private lateinit var list: MutableList<String>
    private lateinit var adapter: ListAdapter
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var switch: Switch

    companion object {
        var music_select = 0
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        soundsViewModel = ViewModelProviders.of(this).get(SoundsViewModel::class.java)
        val root = inflater.inflate(com.example.sleewell.R.layout.fragment_sounds, container, false)

        listView = root.findViewById(com.example.sleewell.R.id.music_list_view) as ListView
        list = ArrayList()

        val fields = com.example.sleewell.R.raw::class.java.fields
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


        //Spotify button :
        var prefs = PreferenceManager.getDefaultSharedPreferences(context)
        switch = root.findViewById(com.example.sleewell.R.id.SpotifyConnect_bt)
        if (prefs.getBoolean("MusicSpotify", false))
            switch.toggle()
        switch.setOnClickListener(View.OnClickListener {
            //Toast.makeText(context, "Ok it's working", Toast.LENGTH_LONG).show()

            if (!switch.isChecked) {
                disconnect()
            } else {

                val connectionParams = ConnectionParams.Builder(CLIENT_ID)
                    .setRedirectUri(REDIRECT_URI)
                    .showAuthView(true)
                    .build()

                SpotifyAppRemote.connect(context, connectionParams,
                    object : Connector.ConnectionListener {

                        override fun onConnected(spotifyAppRemote: SpotifyAppRemote) {
                            mSpotifyAppRemote = spotifyAppRemote
                            Log.d("MainActivity", "Connected! Yay!")

                            connected()

                        }

                        override fun onFailure(throwable: Throwable) {
                            //Log.e("MyActivity", throwable.message, throwable)
                            Toast.makeText(context, "Failed : " + throwable.message, Toast.LENGTH_LONG).show()
                            if (switch.isChecked)
                                switch.toggle()
                            // Something went wrong when attempting to connect! Handle errors here
                        }
                    }
                )
            }
        })


        return root
    }

    private fun connected()
    {
        Toast.makeText(context, "Connected", Toast.LENGTH_LONG).show()
        var prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val myEdit = prefs.edit()
        myEdit.putBoolean("MusicSpotify", true)
        myEdit.commit()
        //Toast.makeText(context, prefs.getBoolean("MusicSpotify", false).toString(), Toast.LENGTH_LONG).show()
    }

    private fun disconnect()
    {
        Toast.makeText(context, "Disconnected", Toast.LENGTH_LONG).show()
        var prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val myEdit = prefs.edit()
        myEdit.putBoolean("MusicSpotify", false)
        myEdit.commit()
        //Toast.makeText(context, prefs.getBoolean("MusicSpotify", false).toString(), Toast.LENGTH_LONG).show()
    }

    override fun onStop() {
        super.onStop()
        SpotifyAppRemote.disconnect(mSpotifyAppRemote)
    }

    override fun onDestroy() {
        mediaPlayer?.stop()
        super.onDestroy()
    }
}