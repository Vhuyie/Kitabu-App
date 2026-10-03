package com.example.kitabu.data.database

import androidx.room.withTransaction
import com.example.kitabu.R
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
                    title = "Python For Absolute Beginners",
                    author = "Andrew Warner",
                    category = "Technology",
                    imageResId = R.drawable.python_for_absolute_beginners,
                    isAvailable = true
                ),

                BookEntity(
                    title = "Kotlin in Depth",
                    author = "Aleksei Sedunov",
                    category = "Technology",
                    imageResId = R.drawable.kotlin_in_depth,
                    isAvailable = true
                ),

                BookEntity(
                    title = "The C++ Programming Language",
                    author = "Bjarne Stroustrup",
                    category = "Business",
                    imageResId = R.drawable.the_programming_language,
                    isAvailable = true
                ),

                BookEntity(
                    title = "HTML, CSS, & JavaScript All-in-One For Dummies",
                    author = "Paul McFedries",
                    category = "Academic",
                    imageResId = R.drawable.javascript_all_in_one_for_dummies,
                    isAvailable = true
                ),

                BookEntity(
                    title = "Oxford International Computing",
                    author = "Alison Page, Karl Held, Diane Levine, Howard Lincoln",
                    category = "Academic",
                    imageResId = R.drawable.oxford,
                    isAvailable = true
                ),

                BookEntity(
                    title = "Fundamentals of DevOps and Software Delivery",
                    author = "Yevgeniy Brikman",
                    category = "Business",
                    imageResId = R.drawable.fundamentals_of_devops_and_software_delivery,
                    isAvailable = true
                ),

                BookEntity(
                    title = "Mobile Application Development",
                    author = "James Wilson",
                    category = "Technology",
                    imageResId = R.drawable.kotlin_in_depth,
                    isAvailable = true
                ),

                BookEntity(
                    title = "Information Systems",
                    author = "Kenneth Laudon",
                    category = "Academic",
                    imageResId = R.drawable.python_for_absolute_beginners,
                    isAvailable = true
                )
            )
        )
    }
}