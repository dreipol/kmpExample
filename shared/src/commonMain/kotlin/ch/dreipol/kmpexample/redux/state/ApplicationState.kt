package ch.dreipol.kmpexample.redux.state

import ch.dreipol.dreimultiplatform.PersistentKeyValueStore
import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.AbstractNavigationState
import ch.dreipol.kmpexample.getPlatform
import ch.dreipol.kmpexample.redux.Screen
import ch.dreipol.kmpexample.util.userName

data class ApplicationState(
    val navigationState: NavigationState = NavigationState(),
    val userName: String = "",
    val viewStates: ViewStates = ViewStates(),
) {
    constructor(keyValueStore: PersistentKeyValueStore): this(userName = keyValueStore.userName ?: getPlatform().name)
}

data class NavigationState(override val pushedScreens: List<Screen> = emptyList()) : AbstractNavigationState<Screen>() {
    override val homeScreen: Screen = Screen.Chat
}

data class ViewStates(
    val chatViewState: ChatViewState = ChatViewState(),
)

enum class SendStatus {
    IDLE, SENDING, FAILED
}

data class ChatViewState(
    val message: String = "",
    val fetchError: Boolean = false,
    val sendStatus: SendStatus = SendStatus.IDLE,
) {
    val sendEnabled: Boolean
        get() = message.isBlank().not()
}