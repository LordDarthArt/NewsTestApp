package tk.lorddarthart.newstestapp.app

import android.app.Application

class NewsTestApp: Application() {
    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

    companion object {
        lateinit var INSTANCE: NewsTestApp
    }
}