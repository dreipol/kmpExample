package ch.dreipol.kmpexample.redux.state

import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.AbstractNavigationState
import ch.dreipol.kmpexample.redux.Screen

data class NavigationState(override val pushedScreens: List<Screen> = emptyList()) : AbstractNavigationState<Screen>() {
    override val homeScreen: Screen = Screen.HomeScreen
}

data class ApplicationState(
    val navigationState: NavigationState = NavigationState(),
)