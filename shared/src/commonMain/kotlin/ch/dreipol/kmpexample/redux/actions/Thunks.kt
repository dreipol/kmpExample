package ch.dreipol.kmpexample.redux.actions

import ch.dreipol.dreimultiplatform.reduxkotlin.ThunkAction
import ch.dreipol.kmpexample.redux.state.ApplicationState
import org.reduxkotlin.Thunk

fun createThunkAction(thunk: Thunk<ApplicationState>): ThunkAction<ApplicationState> = ThunkAction(thunk)