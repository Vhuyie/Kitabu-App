package com.example.kitabu.data.repository

import androidx.room.Query
import androidx.room.withTransaction
import com.example.kitabu.data.database.AppDatabase
import com.example.kitabu.data.entity.BookEntity
import com.example.kitabu.data.entity.BookingEntity
import com.example.kitabu.data.entity.BookingStatus
import com.example.kitabu.data.entity.BookingWithBook
import com.example.kitabu.data.entity.StudentEntity
import kotlinx.coroutines.flow.Flow

class LibraryRepository(
    private val database: AppDatabase
) {

    private val studentDao = database.studentDao()
    private val bookDao = database.bookDao()
    private val bookingDao = database.bookingDao()

    fun getBooks(): Flow<List<BookEntity>> =
        bookDao.getAllBooks()

    fun searchBooks(query: String): Flow<List<BookEntity>> =
        bookDao.searchBooks(query)

    fun getActiveBookings(): Flow<List<BookingWithBook>> =
        bookingDao.getActiveBookingsWithBooks()

    suspend fun reserveBook(
        book: BookEntity,
        userName: String,
        durationDays: Int
    ) {

        database.withTransaction {

            val now = System.currentTimeMillis()

            val deadline =
                now + durationDays * 24L * 60L * 60L * 1000L

            val booking = BookingEntity(
                bookOwnerId = book.bookId,
                userName = userName,
                bookingDate = now,
                returnDeadline = deadline,
                status = BookingStatus.ACTIVE
            )

            bookingDao.insertBooking(booking)

            bookDao.updateAvailability(
                bookId = book.bookId,
                available = false
            )
        }
    }

    suspend fun renewBooking(
        bookingId: Int,
        additionalDays: Int
    ) {

        bookingDao.getBookingById(bookingId)?.let { booking ->

            val newDeadline =
                booking.returnDeadline +
                        additionalDays * 24L * 60L * 60L * 1000L

            bookingDao.renewBooking(
                bookingId,
                newDeadline
            )
        }
    }

    suspend fun returnBook(
        bookingId: Int
    ) {

        database.withTransaction {

            val booking =
                bookingDao.getBookingById(bookingId)
                    ?: return@withTransaction

            bookingDao.updateStatus(
                bookingId,
                BookingStatus.RETURNED
            )

            bookDao.updateAvailability(
                bookId = booking.bookOwnerId,
                available = true
            )
        }
    }

    suspend fun cancelBooking(
        bookingId: Int
    ) {

        database.withTransaction {

            val booking =
                bookingDao.getBookingById(bookingId)
                    ?: return@withTransaction

            bookingDao.cancelPendingBooking(bookingId)

            bookDao.updateAvailability(
                bookId = booking.bookOwnerId,
                available = true
            )
        }
    }

    suspend fun addBook(book: BookEntity) {
        bookDao.insertBook(book)
    }

    suspend fun updateBook(book: BookEntity) {
        bookDao.updateBook(book)
    }

    suspend fun deleteBook(book: BookEntity) {
        bookDao.deleteBook(book)
    }

    suspend fun registerStudent(
        fullName: String,
        studentNumber: String,
        email: String,
        username: String,
        password: String
    ): Result<Unit> {

        val existingUsername =
            studentDao.getStudentByUsername(username)

        if (existingUsername != null) {
            return Result.failure(
                Exception("Username already exists.")
            )
        }

        val existingStudentNumber =
            studentDao.getStudentByStudentNumber(studentNumber)

        if (existingStudentNumber != null) {
            return Result.failure(
                Exception("Student number already exists.")
            )
        }

        val existingEmail =
            studentDao.getStudentByEmail(email)

        if (existingEmail != null) {
            return Result.failure(
                Exception("Email address already exists.")
            )
        }

        return try {

            studentDao.insertStudent(
                StudentEntity(
                    fullName = fullName,
                    studentNumber = studentNumber,
                    email = email,
                    username = username,
                    password = password
                )
            )

            Result.success(Unit)

        } catch (e: Exception) {

            Result.failure(e)
        }
    }

    suspend fun loginStudent(
        username: String,
        password: String
    ): StudentEntity? {

        return studentDao.loginStudent(
            username = username,
            password = password
        )
    }

}