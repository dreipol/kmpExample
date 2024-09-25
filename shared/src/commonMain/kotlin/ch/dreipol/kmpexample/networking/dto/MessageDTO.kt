package ch.dreipol.kmpexample.networking.dto

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class MessageDTO(
    val content: String,
    val user: String,
    @SerialName("date_added")
    val timestamp: Instant,
)
