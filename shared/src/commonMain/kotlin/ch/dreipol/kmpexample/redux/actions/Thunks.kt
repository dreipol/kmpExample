package ch.dreipol.kmpexample.redux.actions

import ch.dreipol.dreimultiplatform.coroutines.ioDispatcher
import ch.dreipol.dreimultiplatform.reduxkotlin.ThunkAction
import ch.dreipol.kmpexample.getAppConfiguration
import ch.dreipol.kmpexample.redux.state.ApplicationState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.reduxkotlin.Thunk

private val networkAndDbScope = CoroutineScope(ioDispatcher)

fun createThunkAction(thunk: Thunk<ApplicationState>): ThunkAction<ApplicationState> = ThunkAction(thunk)

fun sendMessageThunk(): Thunk<ApplicationState> = { dispatch, getState, _ ->
    val user = getState().userName
    val message = getState().viewStates.chatViewState.message
    println("Sending $message ($user)")
    dispatch(ChatAction.SendStarted)
    networkAndDbScope.launch {
        val success = getAppConfiguration().chatUseCase.sendMessage(message = message, user = user)
        if (success) {
            dispatch(ChatAction.SendSuccessful)
        } else {
            dispatch(ChatAction.SendFailed)
        }
    }
}

fun loadAllThunk(): Thunk<ApplicationState> = { dispatch, _, _ ->
    networkAndDbScope.launch {
        val success = getAppConfiguration().chatUseCase.getMessages()
        if (!success) {
            dispatch(ChatAction.FetchFailed)
        }
    }
}