package ch.dreipol.kmpexample.database

import ch.dreipol.kmpexample.AppConfiguration
import ch.dreipol.kmpexample.sqldelight.Database
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

actual class DriverFactory : DriverCreator {
    actual override fun createDriver(): SqlDriver {
        return NativeSqliteDriver(Database.Schema, AppConfiguration.databaseFileName)
    }
}