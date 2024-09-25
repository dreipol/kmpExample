package ch.dreipol.kmpexample.redux.reducers

import ch.dreipol.kmpexample.redux.state.ApplicationState
import ch.dreipol.kmpexample.redux.state.ViewStates
import org.reduxkotlin.Reducer

internal val rootReducer: Reducer<ApplicationState> = { state, action ->
    val navigationState = navigationReducer(state.navigationState, action)
    val userName = profileReducer(state.userName, action)
    val viewStates = viewStatesReducer(state.viewStates, action)
    state.copy(
        navigationState = navigationState,
        userName = userName,
        viewStates = viewStates,
    )
}

internal val viewStatesReducer: Reducer<ViewStates> = { state, action ->
    val chatViewState = chatViewStateReducer(state.chatViewState, action)
    state.copy(chatViewState = chatViewState)
}