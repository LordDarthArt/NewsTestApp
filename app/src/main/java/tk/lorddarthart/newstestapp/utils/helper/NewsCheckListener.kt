package tk.lorddarthart.newstestapp.utils.helper

import android.app.NotificationManager
import android.content.AbstractThreadedSyncAdapter
import android.content.Context
import android.widget.CheckBox
import android.widget.CompoundButton
import tk.lorddarthart.newstestapp.app.model.response.Item
import tk.lorddarthart.newstestapp.app.view.activity.MainActivity
import tk.lorddarthart.newstestapp.app.view.fragment.news.adapter.NewsListAdapter

class NewsCheckListener(
    private val adapter: NewsListAdapter,
    private val checkbox: CheckBox,
    private val mainActivity: MainActivity,
    private val position: Int,
    private val notificationTitle: String,
    private val notificationSubtitle: String
) : CompoundButton.OnCheckedChangeListener {
    private var notificationManager: NotificationManager? = null

    override fun onCheckedChanged(
        buttonView: CompoundButton,
        isChecked: Boolean
    ) {
        if (isChecked) {
            checkbox.isChecked = true
            adapter.selectedPosition = position
            adapter.notifyDataSetChanged()
            PushNotifications(mainActivity).getNotified(mainActivity, notificationTitle, notificationSubtitle, adapter.selectedPosition!!)
        } else {
            notificationManager = mainActivity.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager!!.cancel(PushNotifications(mainActivity).uniqueId)
            checkbox.isChecked = false
        }
        buttonView.isChecked = isChecked

    }
}