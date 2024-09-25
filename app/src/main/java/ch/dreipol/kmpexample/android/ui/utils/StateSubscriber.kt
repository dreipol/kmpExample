package ch.dreipol.kmpexample.android.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import ch.dreipol.dreimultiplatform.reduxkotlin.subscribeChanges
import ch.dreipol.kmpexample.redux.ApplicationStore
import ch.dreipol.kmpexample.redux.state.ApplicationState

@Composable
fun <T> subscribeAsState(store: ApplicationStore, selector: (ApplicationState) -> T): State<T> {
    val state = remember {
        mutableStateOf(selector(store.state))
    }
    DisposableEffect(Unit) {
        val cancelSubscription = store.subscribeChanges(selector) {
            state.value = selector(store.state)
        }
        onDispose {
            cancelSubscription()
        }
    }
    return state
}