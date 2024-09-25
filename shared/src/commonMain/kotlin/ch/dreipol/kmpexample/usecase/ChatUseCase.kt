package ch.dreipol.kmpexample.usecase

import ch.dreipol.kmpexample.database.ChatMessageDataStore
import ch.dreipol.kmpexample.networking.api.ChatApi
import ch.dreipol.kmpexample.networking.dto.MessageDTO
import ch.dreipol.kmpexample.sqldelight.ChatMessage

private fun MessageDTO.toDb() = ChatMessage(
    content = content,
    user = user,
    timestamp = timestamp,
)

internal class ChatUseCase(private val api: ChatApi) {
    suspend fun sendMessage(message: String, user: String): Boolean {
        val createdMessage = runCatching {
            api.sendMessage(message = message, user = user)
        }.getOrNull() ?: return false

        ChatMessageDataStore.addMessage(createdMessage.toDb())

        return true
    }

    suspend fun getMessages(): Boolean {
        val messages = runCatching {
            api.getMessages()
        }.getOrNull() ?: return false

        ChatMessageDataStore.addAll(messages.map { it.toDb() })

        return true
    }
}