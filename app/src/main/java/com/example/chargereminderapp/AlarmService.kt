package com.example.chargereminderapp

    import android.app.Service
    import android.content.Intent
    import android.media.Ringtone
    import android.media.RingtoneManager
    import android.os.Handler
    import android.os.IBinder
    import android.os.Looper

class AlarmService : Service() {
    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Create a notification
        val notification = NotificationCompat.Builder(this, "channel_id")
            .setContentTitle("Charge Reminder")
            .setContentText("Battery level reached 80%")
            .setSmallIcon(R.drawable.ic_notification)
            .build()

        // Start the service in the foreground
        startForeground(1, notification)

        // Play a sound (e.g., a ringtone)
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        val ringtone = RingtoneManager.getRingtone(this, alarmSound)
        ringtone.play()

        // Stop service after 10 seconds
        Handler(Looper.getMainLooper()).postDelayed({
            ringtone.stop()
            stopSelf()
        }, 10000)

        return START_STICKY
    }
}
