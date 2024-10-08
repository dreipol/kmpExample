package ch.dreipol.kmpexample.redux.reducers

import ch.dreipol.kmpexample.redux.Screen
import ch.dreipol.kmpexample.redux.actions.NavigationAction
import ch.dreipol.kmpexample.redux.state.NavigationState
import org.reduxkotlin.Reducer

internal val navigationReducer: Reducer<NavigationState> = { state, action ->
    when (action) {
        NavigationAction.Back -> state.copy(state.pushedScreens.dropLast(1))
        NavigationAction.ToProfile -> state.push(Screen.Profile)
        else -> state
    }
}

private fun NavigationState.push(screen: Screen): NavigationState = copy(
    pushedScreens =  pushedScreens + listOf(screen),
)