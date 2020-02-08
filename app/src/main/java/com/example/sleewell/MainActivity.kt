package com.example.sleewell

import android.app.NotificationManager
import android.app.PendingIntent
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiManager
import android.nfc.NfcAdapter
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.google.android.material.bottomnavigation.BottomNavigationView


const val EXTRA_MESSAGE = 0

class MainActivity : AppCompatActivity() {

    var mnfcAdapter : NfcAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mnfcAdapter = NfcAdapter.getDefaultAdapter(this)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_alarms, R.id.navigation_sounds, R.id.navigation_stats
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        //enableForegroundDispatchSystem()
    }

    fun StartSleep(v : View) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        if (prefs.all["SleepMode"] == true){
            enableDnd(true)
        }
        if (prefs.all["wifi"] == true){
            enableWifi(false)
        }
        if (prefs.all["bluetooth"] == true){
            enableBluetooth(false)
        }
        Toast.makeText(applicationContext, "¨Phone asleep", Toast.LENGTH_LONG).show()
        val intent = Intent(this, ProtoActivated::class.java)
        startActivity(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        super.onNewIntent(intent)

        if (intent != null) {
            if (intent.hasExtra(NfcAdapter.EXTRA_TAG)) {
                if (prefs.all["SleepMode"] == true){
                    enableDnd(true)
                }
                if (prefs.all["wifi"] == true && !prefs.getBoolean("MusicSpotify", false)){
                    enableWifi(false)
                }
                if (prefs.all["bluetooth"] == true){
                    enableBluetooth(false)
                }
                Toast.makeText(applicationContext, "¨Phone asleep", Toast.LENGTH_LONG).show()
                val intent = Intent(this, ProtoActivated::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_bar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_settings) {
            //disableForegroundDispatchSystem()
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun enableWifi(value: Boolean) {
        val wifiManager : WifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q)
            wifiManager.setWifiEnabled(value)
    }

    private fun enableBluetooth(value: Boolean) {
        val manager = BluetoothAdapter.getDefaultAdapter()

        if (!value && manager.isEnabled) {
            manager.disable()
        } else if (value && !manager.isEnabled) {
            manager.enable()
        }
    }

    private fun enableDnd(value: Boolean) {
        val notificationManager : NotificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Check if the notification policy access has been granted for the app.
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return
        if (!notificationManager.isNotificationPolicyAccessGranted) {
            val intent = Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
            startActivity(intent)
        }
        if (value) {
            notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_ALARMS)
        } else {
            notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_ALL)
        }
    }

    override fun onResume() {
        super.onResume()
        enableForegroundDispatchSystem()
    }

    override fun onPause() {
        super.onPause()
        disableForegroundDispatchSystem()
    }

    private fun enableForegroundDispatchSystem() {
        val intent =
            Intent(this, MainActivity::class.java).addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
        val intentFilters = arrayOf<IntentFilter>()
        mnfcAdapter?.enableForegroundDispatch(this, pendingIntent, intentFilters, null)
    }

    private fun disableForegroundDispatchSystem() {
        mnfcAdapter?.disableForegroundDispatch(this)
    }
}
