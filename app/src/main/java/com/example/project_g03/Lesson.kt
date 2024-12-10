package com.example.project_g03

import java.io.Serializable

data class Lesson(
    val number: Int,
    val name: String,
    val duration: String,
    val content: String,
    var isCompleted: Boolean
): Serializable
