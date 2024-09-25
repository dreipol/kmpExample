package ch.dreipol.kmpexample.android.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ch.dreipol.kmpexample.android.ui.utils.subscribeAsState
import ch.dreipol.kmpexample.redux.ApplicationStore
import ch.dreipol.kmpexample.redux.actions.ProfileAction

@Composable
fun ProfilePage(store: ApplicationStore) {
    val reduxName by subscribeAsState(store) { it.userName }
    var userName by remember { mutableStateOf(reduxName) }
    LaunchedEffect(userName) {
        if (userName != reduxName) {
            store.dispatch(ProfileAction.SetName(userName))
        }
    }
    LaunchedEffect(reduxName) {
        userName = reduxName
    }

    TextField(
        modifier = Modifier.padding(all = 8.dp),
        value = userName,
        onValueChange = { userName = it },
    )
}