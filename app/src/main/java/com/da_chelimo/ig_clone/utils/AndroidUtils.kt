package com.da_chelimo.ig_clone.utils

import android.os.Build
import android.os.Bundle
import java.io.Serializable

fun <T> Bundle.getSerializableCompat(key: String, clazz: Class<T>) =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getSerializable(key, clazz::class.java) as T
    } else {
        getSerializable(key) as T
    }