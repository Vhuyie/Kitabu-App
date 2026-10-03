package com.example.kitabu.data.entity

import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "students",
    indices = [
        Index(value = ["studentNumber"], unique = true),
        Index(value = ["username"], unique = true),
        Index(value = ["email"], unique = true)
    ]
)
data class StudentEntity(
    @androidx.room.PrimaryKey(autoGenerate = true)
    val studentId: Int = 0,

    val fullName: String,

    val studentNumber: String,

    val email: String,

    val username: String,

    val password: String
)