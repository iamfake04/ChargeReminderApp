package com.example.chargereminderapp


    import android.content.BroadcastReceiver
    import android.content.Context
    import android.content.Intent
    import android.os.BatteryManager

    class BatteryReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
            val isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING

            // Get battery level
            val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)

            // Update progress ring
            if (context is MainActivity) {
                context.updateProgressRing(level)
            }

            // Trigger alarm at 80% while charging
            if (isCharging && level >= 80) {
                val alarmIntent = Intent(context, AlarmService::class.java)
                context.startService(alarmIntent)
            }
        }
    }
