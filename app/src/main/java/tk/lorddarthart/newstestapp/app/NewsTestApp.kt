package tk.lorddarthart.newstestapp.app

import android.app.Application
import android.content.Intent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import tk.lorddarthart.newstestapp.app.view.activity.MainActivity
import javax.inject.Inject

class NewsTestApp: Application(), HasAndroidInjector {
    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    lateinit var currentActivity: MainActivity

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)
    }

    override fun androidInjector() = androidInjector

    companion object {
        var notificationIntent: Intent? = null
    }
}