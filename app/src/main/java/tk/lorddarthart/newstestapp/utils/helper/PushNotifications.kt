package tk.lorddarthart.newstestapp.utils.helper

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat

import android.content.Context.NOTIFICATION_SERVICE
import tk.lorddarthart.newstestapp.R
import tk.lorddarthart.newstestapp.app.NewsTestApp
import tk.lorddarthart.newstestapp.app.view.activity.MainActivity

class PushNotifications(activity: MainActivity) {
    private lateinit var notification: NotificationCompat.Builder
    val uniqueId = 45419
    val mainActivity = activity

    fun getNotified(context: Context, title: String, body: String, position: Int) {
        notification = NotificationCompat.Builder(context, "notify_001")
        with (notification) {
            setOngoing(false)
            setWhen(System.currentTimeMillis())
            setContentTitle(title)
            setContentText(body)
            setSmallIcon(R.drawable.ic_launcher_foreground)
            setStyle(NotificationCompat.BigTextStyle().bigText(body))
            priority = NotificationCompat.PRIORITY_HIGH
            setContentIntent(null)
            NewsTestApp.notificationIntent = Intent(context, MainActivity::class.java)
            with (NewsTestApp.notificationIntent) {
                this?.putExtra("checked", true)
                this?.putExtra("position", position)
                this?.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
            val lol = PendingIntent.getActivity(context, 0, NewsTestApp.notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT)
            addAction(0, "Open", lol)
            setAutoCancel(true)
        }
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
