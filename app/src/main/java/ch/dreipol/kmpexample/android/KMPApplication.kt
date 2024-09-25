package ch.dreipol.kmpexample.android

import android.app.Application
import ch.dreipol.dreimultiplatform.SharedPreferencesKeyValueStore
import ch.dreipol.kmpexample.AppConfigurationBuilder
import ch.dreipol.kmpexample.database.DriverFactory
import ch.dreipol.kmpexample.initApp
import ch.dreipol.kmpexample.redux.createApplicationStore

class KMPApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val persistentKeyValueStore = SharedPreferencesKeyValueStore(this)
        val store = createApplicationStore(persistentKeyValueStore)

        initApp(
            AppConfigurationBuilder(
                store = store,
                driverFactory = DriverFactory(this),
                persistentKeyValueStore = persistentKeyValueStore,
            ),
        )
    }
}