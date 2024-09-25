package ch.dreipol.kmpexample

import ch.dreipol.kmpexample.networking.ServiceFactory
import ch.dreipol.kmpexample.networking.api.ChatApi
import ch.dreipol.kmpexample.redux.ApplicationStore
import ch.dreipol.kmpexample.usecase.ChatUseCase

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
        store = store,
        chatUseCase = ChatUseCase(ChatApi(serviceFactory = ServiceFactory)),
    )
}

class AppConfiguration internal constructor(
    val store: ApplicationStore,
    internal val chatUseCase: ChatUseCase,
)