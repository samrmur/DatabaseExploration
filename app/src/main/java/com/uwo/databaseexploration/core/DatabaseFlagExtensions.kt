package com.uwo.databaseexploration.core

import android.content.SharedPreferences
import androidx.core.content.edit

private const val DATABASE_USE_ROOM_FLAG = "USE_ROOM"

fun SharedPreferences.shouldUseRoom(): Boolean {
    return getBoolean(DATABASE_USE_ROOM_FLAG, true)
}

fun SharedPreferences.setToRoom() {
    edit {
        putBoolean(DATABASE_USE_ROOM_FLAG, true)
    }
}

fun SharedPreferences.setToRealm() {
    edit {
        putBoolean(DATABASE_USE_ROOM_FLAG, false)
    }
}
