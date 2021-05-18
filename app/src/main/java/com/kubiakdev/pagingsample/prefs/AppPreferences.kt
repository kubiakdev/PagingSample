package com.kubiakdev.pagingsample.prefs

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

class AppPreferences @Inject constructor(private val prefs: SharedPreferences) {

    var lastDbUpdateInMillis: Long
        get() = prefs.getLong(KEY_LAST_DB_UPDATE_IN_MILLIS, 0)
        set(value) {
            prefs.edit { putLong(KEY_LAST_DB_UPDATE_IN_MILLIS, value) }
        }

    private companion object {
        private const val KEY_LAST_DB_UPDATE_IN_MILLIS = "last_db_update_in_millis"
    }
}