package ch.dreipol.kmpexample.database

import ch.dreipol.dreimultiplatform.reduxkotlin.flowOf
import ch.dreipol.kmpexample.getAppConfiguration
import ch.dreipol.kmpexample.sqldelight.ChatMessage
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

object ChatMessageDataStore {
    private val queries = DatabaseHelper.database.chatMessageQueries

    internal fun addMessage(chatMessage: ChatMessage) {
        queries.insertOrUpdate(
            content = chatMessage.content,
            user = chatMessage.user,
            timestamp = chatMessage.timestamp,
        )
    }

    internal fun addAll(chatMessages: List<ChatMessage>) {
        queries.transaction {
            chatMessages.forEach { addMessage(it) }
        }
    }

    fun getAll(): Flow<List<ChatMessage>> =
        queries.getAll().asFlow().mapToList()
            .combine(getAppConfiguration().store.flowOf { it.viewStates.chatViewState.message }) { listOfChatMessages, composedMessage ->
                listOfChatMessages.map { message ->
                    message.copy(content = message.content + composedMessage)
                }
            }
}