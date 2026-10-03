package com.example.kitabu.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class BookEntity(

    @PrimaryKey(autoGenerate = true)
    val bookId: Int = 0,

    val title: String,

    val author: String,

    val category: String,

    val imageResId: Int = 0,

    val isAvailable: Boolean = true
)