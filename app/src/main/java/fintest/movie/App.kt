package fintest.movie

import android.app.Activity
import android.app.Application
import android.content.*
import fintest.movie.features.dashboard.di.appModule
import fintest.movie.features.dashboard.di.repoModule
import fintest.movie.features.dashboard.di.viewModelModule
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class App : Application() {
    private val prefs by inject<SharedPreferences>()

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        //Stetho.initializeWithDefaults(this)
        startKoin {
            androidContext(this@App)
            modules(listOf(appModule, repoModule, viewModelModule))
        }
    }

    companion object {
        val CHANNEL_ID = "autoStartServiceChannel"
        val CHANNEL_NAME = "Auto Start Service Channel"
        var appContext: Context? = null
        var mCurrentActivity: Activity? = null

        fun getCurrentActivity(): Activity? {
            return mCurrentActivity
        }

        fun setCurrentActivity(mCurrentActivity1: Activity?) {
            mCurrentActivity = mCurrentActivity1
        }
    }
}