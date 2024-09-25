package ch.dreipol.kmpexample.redux.actions

sealed class ProfileAction {
    data class SetName(val newName: String) : ProfileAction()
}