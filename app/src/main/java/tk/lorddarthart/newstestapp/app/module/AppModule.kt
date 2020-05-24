package tk.lorddarthart.newstestapp.app.module

import android.app.Application
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.android.DaggerApplication
import tk.lorddarthart.newstestapp.app.NewsTestApp
import tk.lorddarthart.newstestapp.app.view.activity.MainActivity
import tk.lorddarthart.newstestapp.app.view.fragment.news.NewsFragment
import tk.lorddarthart.newstestapp.app.view.fragment.news.adapter.NewsListAdapter
import tk.lorddarthart.newstestapp.app.view.fragment.splash.SplashFragment
import tk.lorddarthart.newstestapp.utils.helper.PushNotifications


@Module
abstract class AppModule {
    @ContributesAndroidInjector
    abstract fun contributesMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributesNewsFragment(): NewsFragment

    @ContributesAndroidInjector
    abstract fun contributesSplashFragment(): SplashFragment

    @ContributesAndroidInjector
    abstract fun contributesNewsListAdapter(): NewsListAdapter

    companion object {
        @Provides
        fun provideThePushNotifications(application: NewsTestApp): PushNotifications {
            return PushNotifications(application.currentActivity)
        }
    }
}