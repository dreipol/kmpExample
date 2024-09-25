package ch.dreipol.kmpexample.android.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ch.dreipol.kmpexample.android.ui.utils.subscribeAsState
import ch.dreipol.kmpexample.database.ChatMessageDataStore
import ch.dreipol.kmpexample.previewData.ChatMessagePreviews
import ch.dreipol.kmpexample.redux.ApplicationStore
import ch.dreipol.kmpexample.redux.actions.ChatAction
import ch.dreipol.kmpexample.redux.actions.loadAllThunk
import ch.dreipol.kmpexample.redux.actions.sendMessageThunk
import ch.dreipol.kmpexample.redux.createPreviewStore
import ch.dreipol.kmpexample.sqldelight.ChatMessage

@Composable
fun ChatContainer(store: ApplicationStore) {
    val messages by ChatMessageDataStore.getAll().collectAsState(initial = emptyList())
    ChatPage(store, messages = messages)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatPage(store: ApplicationStore, messages: List<ChatMessage>) {
    val reduxComposer by subscribeAsState(store) { it.viewStates.chatViewState.message }
    var composer by remember { mutableStateOf("") }
    LaunchedEffect(composer) {
        if (composer != reduxComposer) {
            store.dispatch(ChatAction.SetMessage(composer))
        }
    }
    LaunchedEffect(reduxComposer) {
        composer = reduxComposer
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Chat")
                },
                actions = {
                    Button(
                        onClick = { store.dispatch(loadAllThunk()) },
                    ) { Text("Refresh") }
                },
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            ) {
                for (message in messages) {
                    item {
                        ChatRow(message = message)
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                TextField(
                    modifier = Modifier
                        .weight(1f),
                    value = composer,
                    onValueChange = { composer = it },
                )
                Button(
                    onClick = { store.dispatch(sendMessageThunk()) },
                ) {
                    Text("Send")
                }
            }
        }
    }
}

@Composable
fun ChatRow(message: ChatMessage) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            modifier = Modifier
                .padding(top = 8.dp),
            text = message.user ?: "",
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = message.content ?: "",
            textAlign = TextAlign.End,
        )
        Divider()
    }
}

@Preview
@Composable
fun ChatPagePreview() {
    ChatPage(
        store = createPreviewStore(),
        messages = ChatMessagePreviews.list,
    )
}