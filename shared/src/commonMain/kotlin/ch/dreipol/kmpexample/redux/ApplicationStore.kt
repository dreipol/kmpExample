package ch.dreipol.kmpexample.redux

import ch.dreipol.dreimultiplatform.reduxkotlin.actionLoggingMiddleware
import ch.dreipol.dreimultiplatform.reduxkotlin.convertThunkActionMiddleware
import ch.dreipol.dreimultiplatform.reduxkotlin.createMainThreadStore
import ch.dreipol.kmpexample.redux.reducers.rootReducer
import ch.dreipol.kmpexample.redux.state.ApplicationState
import org.reduxkotlin.Store
import org.reduxkotlin.applyMiddleware
import org.reduxkotlin.compose
import org.reduxkotlin.createThunkMiddleware

typealias ApplicationStore = Store<ApplicationState>

fun createPreviewStore(): ApplicationStore = createApplicationStore()

fun createApplicationStore(): ApplicationStore {
    return createMainThreadStore(
        rootReducer,
        ApplicationState(),
        compose(
            listOf(
                applyMiddleware(
                    convertThunkActionMiddleware(),
                    createThunkMiddleware(),
                    actionLoggingMiddleware(),
                ),
            ),
        ),
    )
}

val ApplicationStore.applicationState: ApplicationState
    get() = getState()