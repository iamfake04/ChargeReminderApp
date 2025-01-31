package com.example.chargereminderapp

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var batteryReceiver: BatteryReceiver
    private lateinit var progressRing: ProgressBar
    private lateinit var tvStatus: TextView
    private var isReceiverRegistered = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Set the layout

        // Initialize views
        progressRing = findViewById(R.id.progressRing)
        tvStatus = findViewById(R.id.tvStatus)
        val switch = findViewById<Switch>(R.id.switchEnableReminder)

        // Initialize BroadcastReceiver
        batteryReceiver = BatteryReceiver()

        switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Register battery receiver
                val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
                registerReceiver(batteryReceiver, filter)
                tvStatus.text = "Status: Active (Plug in to test)"
            } else {
                // Unregister receiver
                if (isReceiverRegistered) {

                    unregisterReceiver(batteryReceiver)
                    isReceiverRegistered = false

                    tvStatus.text = "Status: Inactive"
                }
            }
        }

        // Update progress ring with battery level
        fun updateProgressRing(level: Int) {
            progressRing.progress = level
        }

        override fun onDestroy() {
            super.onDestroy()
            // Unregister receiver to avoid leaks
            if (isReceiverRegistered) {

                unregisterReceiver(batteryReceiver)
            }
        }
    }
}