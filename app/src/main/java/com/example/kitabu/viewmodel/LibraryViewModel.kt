package com.example.kitabu.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kitabu.data.entity.BookEntity
import com.example.kitabu.data.repository.LibraryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LibraryViewModel(
    private val repository: LibraryRepository
) : ViewModel() {

    private val searchQuery = MutableStateFlow("")

    val books: StateFlow<List<BookEntity>> =
        searchQuery
            .flatMapLatest { query ->
                if (query.isBlank()) {
                    repository.getBooks()
                } else {
                    repository.searchBooks(query)
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    // Book reservations waiting for Accept or Cancel
    val pendingBookings =
        repository.getPendingBookings()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    // Books that have been accepted/reserved
    val reservedBookings =
        repository.getReservedBookings()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    fun search(query: String) {
        searchQuery.value = query
    }

    // Reserve a book
    fun reserveBook(
        book: BookEntity,
        userName: String,
        durationDays: Int
    ) {
        viewModelScope.launch {

            repository.reserveBook(
                book = book,
                userName = userName,
                durationDays = durationDays
            )
        }
    }

    // Accept a pending reservation
    fun acceptBooking(
        bookingId: Int
    ) {
        viewModelScope.launch {

            repository.acceptBooking(
                bookingId = bookingId
            )
        }
    }

    // Cancel a pending reservation
    fun cancelBooking(
        bookingId: Int
    ) {
        viewModelScope.launch {

            repository.cancelBooking(
                bookingId = bookingId
            )
        }
    }

    // Renew a reserved book
    fun renewBooking(
        bookingId: Int,
        days: Int = 1
    ) {
        viewModelScope.launch {

            repository.renewBooking(
                bookingId = bookingId,
                additionalDays = days
            )
        }
    }

    // Return a reserved book
    fun returnBook(
        bookingId: Int
    ) {
        viewModelScope.launch {

            repository.returnBook(
                bookingId = bookingId
            )
        }
    }

    // ADMIN - ADD BOOK
    fun addBook(
        title: String,
        author: String,
        category: String
    ) {
        viewModelScope.launch {

            repository.addBook(
                BookEntity(
                    title = title,
                    author = author,
                    category = category,
                    isAvailable = true
                )
            )
        }
    }

    // ADMIN - UPDATE BOOK
    fun updateBook(
        book: BookEntity
    ) {
        viewModelScope.launch {

            repository.updateBook(book)
        }
    }

    // ADMIN - DELETE BOOK
    fun deleteBook(
        book: BookEntity
    ) {
        viewModelScope.launch {

            repository.deleteBook(book)
        }
    }

    // STUDENT REGISTRATION
    fun registerStudent(
        fullName: String,
        studentNumber: String,
        email: String,
        username: String,
        password: String,
        onResult: (Boolean, String) -> Unit
    ) {
        viewModelScope.launch {

            val result = repository.registerStudent(
                fullName = fullName,
                studentNumber = studentNumber,
                email = email,
                username = username,
                password = password
            )

            if (result.isSuccess) {

                onResult(
                    true,
                    "Account created successfully."
                )

            } else {

                onResult(
                    false,
                    result.exceptionOrNull()?.message
                        ?: "Registration failed."
                )
            }
        }
    }

    // STUDENT LOGIN
    fun loginStudent(
        username: String,
        password: String,
        onResult: (Boolean, String) -> Unit
    ) {
        viewModelScope.launch {

            val student = repository.loginStudent(
                username = username,
                password = password
            )

            if (student != null) {

                onResult(
                    true,
                    "Login successful."
                )

            } else {

                onResult(
                    false,
                    "Invalid username or password."
                )
            }
        }
    }
}