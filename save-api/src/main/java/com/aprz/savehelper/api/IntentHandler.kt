package com.aprz.savehelper.api

import android.content.Intent
import android.os.Bundle

/**
 * @author by liyunlei
 *
 * write on 2019/9/28
 *
 * Class desc:
 */

fun getSavedLong(intent: Intent, savedInstanceState: Bundle, key: String): Long {
    return getSavedLong(intent, savedInstanceState, key, 0L)
}

fun getSavedLong(
    intent: Intent?,
    savedInstanceState: Bundle?,
    key: String,
    defaultValue: Long
): Long {
    if (savedInstanceState != null) {
        return savedInstanceState.getLong(key, defaultValue)
    } else if (intent != null) {
        return intent.getLongExtra(key, defaultValue)
    }
    return defaultValue
}

fun getSavedInt(intent: Intent, savedInstanceState: Bundle, key: String): Int {
    return getSavedInt(intent, savedInstanceState, key, 0)
}

fun getSavedInt(
    intent: Intent?,
    savedInstanceState: Bundle?,
    key: String,
    defaultValue: Int
): Int {
    if (savedInstanceState != null) {
        return savedInstanceState.getInt(key, defaultValue)
    } else if (intent != null) {
        return intent.getIntExtra(key, defaultValue)
    }
    return defaultValue
}

fun getSavedDouble(intent: Intent, savedInstanceState: Bundle, key: String): Double {
    return getSavedDouble(intent, savedInstanceState, key, 0.0)
}

fun getSavedDouble(
    intent: Intent?,
    savedInstanceState: Bundle?,
    key: String,
    defaultValue: Double
): Double {
    if (savedInstanceState != null) {
        return savedInstanceState.getDouble(key, defaultValue)
    } else if (intent != null) {
        return intent.getDoubleExtra(key, defaultValue)
    }
    return defaultValue
}

fun getSavedFloat(intent: Intent, savedInstanceState: Bundle, key: String): Float {
    return getSavedFloat(intent, savedInstanceState, key, 0f)
}

fun getSavedFloat(
    intent: Intent?,
    savedInstanceState: Bundle?,
    key: String,
    defaultValue: Float
): Float {
    if (savedInstanceState != null) {
        return savedInstanceState.getFloat(key, defaultValue)
    } else if (intent != null) {
        return intent.getFloatExtra(key, defaultValue)
    }
    return defaultValue
}

fun getSavedString(intent: Intent, savedInstanceState: Bundle, key: String): String {
    return getSavedString(intent, savedInstanceState, key, "")
}

fun getSavedString(
    intent: Intent?,
    savedInstanceState: Bundle?,
    key: String,
    defaultValue: String
): String {
    if (savedInstanceState != null) {
        return savedInstanceState.getString(key, defaultValue)
    } else if (intent != null) {
        return intent.getStringExtra(key) ?: return ""
    }
    return defaultValue
}