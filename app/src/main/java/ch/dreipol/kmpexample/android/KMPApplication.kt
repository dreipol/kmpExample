package ch.dreipol.kmpexample.android

import android.app.Application
import ch.dreipol.kmpexample.AppConfigurationBuilder
import ch.dreipol.kmpexample.initApp
import ch.dreipol.kmpexample.redux.createApplicationStore

class KMPApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val store = createApplicationStore()

        initApp(AppConfigurationBuilder(store))
    }
}