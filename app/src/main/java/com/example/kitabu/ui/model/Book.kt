package com.example.kitabu.ui.model

data class Book(
    val id: Int,
    val title: String,
    val author: String,
    val category: String,
    val imageResId: Int,
    val isAvailable: Boolean
)