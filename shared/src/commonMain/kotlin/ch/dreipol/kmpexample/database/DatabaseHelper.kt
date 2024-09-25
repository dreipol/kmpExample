package ch.dreipol.kmpexample.database

import ch.dreipol.kmpexample.getAppConfiguration
import ch.dreipol.kmpexample.sqldelight.ChatMessage
import ch.dreipol.kmpexample.sqldelight.Database
import com.squareup.sqldelight.ColumnAdapter
import com.squareup.sqldelight.EnumColumnAdapter
import com.squareup.sqldelight.db.SqlDriver
import kotlinx.datetime.Instant

interface DriverCreator {
    fun createDriver(): SqlDriver
}

expect class DriverFactory : DriverCreator {
    override fun createDriver(): SqlDriver
}

object DatabaseHelper {
    private const val CURRENT_DB_VERSION = 1

    val database = Database(
        driver = getAppConfiguration().driver,
        chatMessageAdapter = ChatMessage.Adapter(
            timestampAdapter = InstantAdapter(),
        ),
    )

    init {
        Database.Schema.migrate(getAppConfiguration().driver, Database.Schema.version, CURRENT_DB_VERSION)
    }
}

class InstantAdapter : ColumnAdapter<Instant, Long> {
    override fun decode(databaseValue: Long): Instant {
        return Instant.fromEpochMilliseconds(databaseValue)
    }

    override fun encode(value: Instant): Long {
        return value.toEpochMilliseconds()
    }
}

class StringListAdapter : ColumnAdapter<List<String>, String> {

    private companion object {
        private const val DELIMITER = ";"
    }

    override fun decode(databaseValue: String): List<String> =
        if (databaseValue.isEmpty()) listOf() else databaseValue.split(DELIMITER)

    override fun encode(value: List<String>): String =
        value.joinToString(DELIMITER)
}

class EnumListAdapter<E : Enum<E>>(private val enumAdapter: EnumColumnAdapter<E>) : ColumnAdapter<List<E>, String> {
    private val listAdapter = StringListAdapter()

    override fun decode(databaseValue: String): List<E> =
        listAdapter.decode(databaseValue).map { enumAdapter.decode(it) }

    override fun encode(value: List<E>): String =
        listAdapter.encode(value.map { enumAdapter.encode(it) })
}

@Suppress("FunctionName")
inline fun <reified E : Enum<E>> EnumListAdapter(): EnumListAdapter<E> = EnumListAdapter(EnumColumnAdapter())

class TypealiasAdapter<T : Any> : ColumnAdapter<T, T> {
    override fun decode(databaseValue: T): T {
        return databaseValue
    }

    override fun encode(value: T): T {
        return value
    }
}