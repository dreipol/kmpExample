package ch.dreipol.kmpexample.database

import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import ch.dreipol.kmpexample.AppConfiguration
import ch.dreipol.kmpexample.sqldelight.Database
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class DriverFactory(private val context: Context) : DriverCreator {
    actual override fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            Database.Schema,
            context,
            AppConfiguration.databaseFileName,
            // Enables foreign keys for ON DELETE CASCADE to work properly
            // (https://github.com/cashapp/sqldelight/issues/1241)
            callback = object : AndroidSqliteDriver.Callback(Database.Schema) {
                override fun onConfigure(db: SupportSQLiteDatabase) {
                    db.setForeignKeyConstraintsEnabled(true)
                }
            },
        )
    }
}