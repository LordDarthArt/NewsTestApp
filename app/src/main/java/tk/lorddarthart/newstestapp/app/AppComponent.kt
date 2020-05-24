package tk.lorddarthart.newstestapp.app

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import tk.lorddarthart.newstestapp.app.module.AppModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    AppModule::class
])
interface AppComponent: AndroidInjector<NewsTestApp> {

    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun application(application: NewsTestApp): Builder
    }
}