package ch.dreipol.kmpexample.redux.reducers

import ch.dreipol.kmpexample.getAppConfiguration
import ch.dreipol.kmpexample.redux.actions.ProfileAction
import ch.dreipol.kmpexample.util.userName
import org.reduxkotlin.Reducer

val profileReducer: Reducer<String> = { state, action ->
    when (action) {
        is ProfileAction.SetName -> {
            getAppConfiguration().persistentKeyValueStore.userName = action.newName
            action.newName
        }
        else -> state
    }
}