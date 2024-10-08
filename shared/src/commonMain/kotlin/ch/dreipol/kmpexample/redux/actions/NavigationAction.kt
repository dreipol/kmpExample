package ch.dreipol.kmpexample.redux.actions

sealed class NavigationAction {
    data object Back : NavigationAction()
    data object ToProfile : NavigationAction()
}