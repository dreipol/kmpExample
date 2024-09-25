package ch.dreipol.kmpexample

import ch.dreipol.kmpexample.redux.ApplicationStore

private lateinit var appConfiguration: AppConfiguration

fun initApp(builder: AppConfigurationBuilder) {
    if (::appConfiguration.isInitialized) {
        throw IllegalStateException("App Configuration is already initialized")
    } else {
        appConfiguration = builder.build()
    }
}

fun getAppConfiguration(): AppConfiguration {
    return appConfiguration
}

data class AppConfigurationBuilder(
    private val store: ApplicationStore,
) {
    internal fun build(): AppConfiguration = AppConfiguration(
        store,
    )
}

data class AppConfiguration(
    val store: ApplicationStore,
)