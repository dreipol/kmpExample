package ch.dreipol.kmpexample.redux.state

import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.AbstractNavigationState
import ch.dreipol.kmpexample.redux.Screen

data class NavigationState(override val pushedScreens: List<Screen> = emptyList()) : AbstractNavigationState<Screen>() {
    override val homeScreen: Screen = Screen.ChatScreen
}

data class ApplicationState(
    val navigationState: NavigationState = NavigationState(),
    val userName: String = "",
    val viewStates: ViewStates = ViewStates(),
)

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
)