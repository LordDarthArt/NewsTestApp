package tk.lorddarthart.newstestapp.app.receiver

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import tk.lorddarthart.newstestapp.app.view.activity.MainActivity
import tk.lorddarthart.newstestapp.app.view.fragment.news.NewsFragment
import tk.lorddarthart.newstestapp.utils.helper.SnackBarNotifications
import java.io.IOException

class ConnectivityChangeReceiver(activity: MainActivity): BroadcastReceiver() {
    private val mainActivity = activity

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context, intent: Intent) {
        val snackBarNotifications = SnackBarNotifications()
        if (mainActivity.currentFragment is NewsFragment) {
            if (!checkInternet()) {
                if (!(mainActivity.currentFragment as NewsFragment).newsPresenter.state) {
                    snackBarNotifications.noInternerConstraintLayout((mainActivity.currentFragment as NewsFragment).fragmentBinding.root)
                    (mainActivity.currentFragment as NewsFragment).newsPresenter.state = true
                }
            } else {
                if ((mainActivity.currentFragment as NewsFragment).newsPresenter.state) {
                    snackBarNotifications.onInternetRestoreConstraintLayout((mainActivity.currentFragment as NewsFragment).fragmentBinding.root)
                    (mainActivity.currentFragment as NewsFragment).newsPresenter.getSomeNews()
                    (mainActivity.currentFragment as NewsFragment).newsPresenter.state = false
                }
            }
        }
    }

    fun checkInternet(): Boolean {
        // Проверка доступа к интернету
        val runtime = Runtime.getRuntime()
        try {
            val ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8")
            val exitValue = ipProcess.waitFor()
            return exitValue == 0
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        return false
    }
}