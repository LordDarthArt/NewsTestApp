package tk.lorddarthart.newstestapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.app.NotificationCompat

import android.content.Context.NOTIFICATION_SERVICE


class PushNotifications {
    private lateinit var notification: NotificationCompat.Builder
    val uniqueId = 45419

    fun getNotified(context: Context, title: String, body: String, position: Int) {
        notification = NotificationCompat.Builder(context, "notify_001")
        notification.setOngoing(false)
        notification.setWhen(System.currentTimeMillis())
        notification.setContentTitle(title)
        notification.setContentText(body)
        notification.setSmallIcon(R.drawable.ic_launcher_foreground)
        notification.setStyle(NotificationCompat.BigTextStyle().bigText(body))
        notification.priority = NotificationCompat.PRIORITY_HIGH
        notification.setContentIntent(null)
        val lal = Intent(context, MainActivity::class.java)
        lal.putExtra("checked", true)
        lal.putExtra("position", position)
        lal.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        val lol = PendingIntent.getActivity(context, 0, lal, PendingIntent.FLAG_CANCEL_CURRENT)
        notification.addAction(0, "Open", lol)
        notification.setAutoCancel(true)
        if (Build.VERSION.SDK_INT >= 21) notification.setVibrate(LongArray(0))
        val nm = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "notify_001",
                "good_news",
                NotificationManager.IMPORTANCE_HIGH
            )
            nm.createNotificationChannel(channel)
        }
        nm.notify(uniqueId, notification.build())
    }
}
