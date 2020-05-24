package tk.lorddarthart.newstestapp.app.view.activity

import android.app.Application
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import dagger.android.AndroidInjection
import tk.lorddarthart.newstestapp.R
import tk.lorddarthart.newstestapp.app.NewsTestApp
import tk.lorddarthart.newstestapp.app.receiver.ConnectivityChangeReceiver
import tk.lorddarthart.newstestapp.app.view.base.BaseActivity
import tk.lorddarthart.newstestapp.app.view.base.BaseFragment
import tk.lorddarthart.newstestapp.app.view.fragment.splash.SplashFragment
import javax.inject.Inject

class MainActivity : BaseActivity() {
    @Inject
    lateinit var app: NewsTestApp

    var netReceiver: ConnectivityChangeReceiver? = null

    val currentFragment: BaseFragment?
        get() {
            return supportFragmentManager.findFragmentById(R.id.container_base) as BaseFragment?
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        app.currentActivity = this

        supportFragmentManager.beginTransaction().replace(R.id.container_base, SplashFragment()).commitNowAllowingStateLoss()
    }

    override fun onResume() {
        super.onResume()
        if (netReceiver == null) {
            netReceiver = ConnectivityChangeReceiver(this)
        }

        val netFilter = IntentFilter()
        netFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE")

        registerReceiver(netReceiver, netFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(netReceiver)
        netReceiver = null
    }
}
