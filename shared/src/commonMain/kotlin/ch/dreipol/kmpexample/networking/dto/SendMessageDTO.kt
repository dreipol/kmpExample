package ch.dreipol.kmpexample.networking.dto

import kotlinx.serialization.Serializable

@Serializable
internal data class SendMessageDTO(
    val content: String,
    val user: String,
)
