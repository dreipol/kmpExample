package ch.dreipol.kmpexample.networking

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.URLBuilder
import io.ktor.http.Url
import io.ktor.http.encodedPath
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

private val logLevel = LogLevel.INFO
private const val baseAddress = "http://172.20.10.2:8000/api/"

internal object ServiceFactory {
    fun client() = HttpClient {
        expectSuccess = true
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                },
            )
        }
        install(Logging) {
            logger = HttpLogger()
            level = logLevel
        }

        defaultRequest {
            url.takeFrom(
                URLBuilder().takeFrom(Url(baseAddress)).apply {
                    encodedPath += url.encodedPath
                },
            )
        }
    }
}

internal class HttpLogger : Logger {
    override fun log(message: String) {
        when (logLevel) {
            LogLevel.ALL, LogLevel.HEADERS -> co.touchlab.kermit.Logger.v { message }
            LogLevel.BODY -> co.touchlab.kermit.Logger.d { message }
            LogLevel.INFO, LogLevel.NONE -> co.touchlab.kermit.Logger.i { message }
        }

    }
}