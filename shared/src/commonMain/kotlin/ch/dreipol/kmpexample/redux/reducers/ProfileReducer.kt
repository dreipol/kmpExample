package ch.dreipol.kmpexample.redux.reducers

import ch.dreipol.kmpexample.redux.actions.ProfileAction
import org.reduxkotlin.Reducer

val profileReducer: Reducer<String> = { state, action ->
    when (action) {
        is ProfileAction.SetName -> action.newName
        else -> state
    }
}