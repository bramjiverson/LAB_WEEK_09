package com.example.lab_week_09

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object MoshiInstance {
    val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory()) // agar Moshi bisa serialize/deserialize Student
        .build()
}
