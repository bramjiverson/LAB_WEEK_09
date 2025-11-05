package com.example.lab_week_09

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Student(
    val name: String,
    val age: Int
)
