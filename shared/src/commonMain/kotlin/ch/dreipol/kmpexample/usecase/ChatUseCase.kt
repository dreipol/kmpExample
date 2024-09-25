package ch.dreipol.kmpexample.usecase

import ch.dreipol.kmpexample.networking.api.ChatApi

internal class ChatUseCase(private val api: ChatApi) {
    suspend fun sendMessage(message: String, user: String): Boolean {
        val createdMessage = runCatching {
            api.sendMessage(message = message, user = user)
        }.getOrNull() ?: return false

        // TODO: save to db
        println(createdMessage)

        return true
    }

    suspend fun getMessages(): Boolean {
        val messages = runCatching {
            api.getMessages()
        }.getOrNull() ?: return false

        // TODO: save to db
        println(messages)

        return true
    }
}