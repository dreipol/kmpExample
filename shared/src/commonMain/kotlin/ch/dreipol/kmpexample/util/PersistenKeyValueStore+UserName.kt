package ch.dreipol.kmpexample.util

import ch.dreipol.dreimultiplatform.PersistentKeyValueStore

private const val USER_NAME_KEY = "USER_NAME_KEY"

var PersistentKeyValueStore.userName: String?
    get() = getString(USER_NAME_KEY)
    set(value) = if (value != null) {
        storeString(value, USER_NAME_KEY)
    } else {
        removeEntry(USER_NAME_KEY)
    }