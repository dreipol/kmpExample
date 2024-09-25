package ch.dreipol.kmpexample.redux.actions

sealed class ChatAction {
    internal data object FetchFailed : ChatAction()
    data object DismissFetchFailed : ChatAction()

    data class SetMessage(val newMessage: String) : ChatAction()

    internal data object SendStarted : ChatAction()
    internal data object SendSuccessful : ChatAction()
    internal data object SendFailed : ChatAction()
    data object DismissSendFailed : ChatAction()
}