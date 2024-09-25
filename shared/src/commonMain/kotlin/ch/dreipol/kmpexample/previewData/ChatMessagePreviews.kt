package ch.dreipol.kmpexample.previewData

import ch.dreipol.kmpexample.sqldelight.ChatMessage
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

object ChatMessagePreviews {
    val list = listOf(
        ChatMessage(
            content = "Hello",
            user = "iOS",
            timestamp = LocalDateTime(year = 2024, monthNumber = 9, dayOfMonth = 26, hour = 9, minute = 0)
                .toInstant(TimeZone.currentSystemDefault()),
        ),
        ChatMessage(
            content = "World",
            user = "Android",
            timestamp = LocalDateTime(year = 2024, monthNumber = 9, dayOfMonth = 26, hour = 9, minute = 5)
                .toInstant(TimeZone.currentSystemDefault()),
        ),
    )
}