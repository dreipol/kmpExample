package ch.dreipol.kmpexample.redux.reducers

import ch.dreipol.kmpexample.redux.state.ApplicationState
import org.reduxkotlin.Reducer

val rootReducer: Reducer<ApplicationState> = { state, action ->
    val navigationState = navigationReducer(state.navigationState, action)
    state.copy(
        navigationState = navigationState,
    )
}