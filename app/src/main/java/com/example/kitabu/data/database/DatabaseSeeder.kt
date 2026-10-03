package com.example.kitabu.data.database

import androidx.room.withTransaction
import com.example.kitabu.data.entity.BookEntity

suspend fun seedDatabase(
    database: AppDatabase
) {

    val bookDao = database.bookDao()

    if (bookDao.getBookById(1) != null) {
        return
    }

    database.withTransaction {

        bookDao.insertBooks(
            listOf(

                BookEntity(
                    title = "Introduction to Programming",
                    author = "Robert Martin",
                    category = "Technology",
                    isAvailable = true
                ),

                BookEntity(
                    title = "Android Development with Kotlin",
                    author = "John Smith",
                    category = "Technology",
                    isAvailable = true
                ),

                BookEntity(
                    title = "Business Management",
                    author = "Peter Jones",
                    category = "Business",
                    isAvailable = true
                ),

                BookEntity(
                    title = "Database Systems",
                    author = "Thomas Connolly",
                    category = "Academic",
                    isAvailable = true
                ),

                BookEntity(
                    title = "Computer Science",
                    author = "William Stallings",
                    category = "Academic",
                    isAvailable = true
                ),

                BookEntity(
                    title = "Entrepreneurship Today",
                    author = "Sarah Brown",
                    category = "Business",
                    isAvailable = true
                ),

                BookEntity(
                    title = "Mobile Application Development",
                    author = "James Wilson",
                    category = "Technology",
                    isAvailable = true
                ),

                BookEntity(
                    title = "Information Systems",
                    author = "Kenneth Laudon",
                    category = "Academic",
                    isAvailable = true
                )
            )
        )
    }
}