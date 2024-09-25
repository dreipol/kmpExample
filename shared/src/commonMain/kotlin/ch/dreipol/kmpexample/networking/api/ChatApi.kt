package ch.dreipol.kmpexample.networking.api

import ch.dreipol.kmpexample.networking.ServiceFactory
import ch.dreipol.kmpexample.networking.dto.MessageDTO
import ch.dreipol.kmpexample.networking.dto.SendMessageDTO
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.path

private const val PATH = "chat/"

internal class ChatApi(serviceFactory: ServiceFactory) {
    private val client = serviceFactory.client()

    suspend fun sendMessage(user: String, message: String): MessageDTO =
        client.post {
            url {
                path(PATH)
            }
            contentType(ContentType.Application.Json)
            setBody(SendMessageDTO(content = message, user = user))
        }.body()

    suspend fun getMessages(): List<MessageDTO> =
        client.get {
            url {
                path(PATH)
            }
        }.body()
}