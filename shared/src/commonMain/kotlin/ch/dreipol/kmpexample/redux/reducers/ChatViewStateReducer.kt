package ch.dreipol.kmpexample.redux.reducers

import ch.dreipol.kmpexample.redux.actions.ChatAction
import ch.dreipol.kmpexample.redux.state.ChatViewState
import ch.dreipol.kmpexample.redux.state.SendStatus
import org.reduxkotlin.Reducer

internal val chatViewStateReducer: Reducer<ChatViewState> = { state, action ->
    when (action) {
        ChatAction.FetchFailed -> state.copy(fetchError = true)
        ChatAction.DismissSendFailed -> state.copy(fetchError = false)
        ChatAction.SendStarted -> state.copy(sendStatus = SendStatus.SENDING)
        ChatAction.SendSuccessful -> state.copy(message = "", sendStatus = SendStatus.IDLE)
        ChatAction.SendFailed -> state.copy(sendStatus = SendStatus.FAILED)
        ChatAction.DismissSendFailed -> state.copy(sendStatus = SendStatus.IDLE)
        else -> state
    }
}