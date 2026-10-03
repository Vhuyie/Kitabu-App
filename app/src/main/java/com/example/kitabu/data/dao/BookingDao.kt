package com.example.kitabu.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.kitabu.data.entity.BookingEntity
import com.example.kitabu.data.entity.BookingStatus
import com.example.kitabu.data.entity.BookingWithBook
import kotlinx.coroutines.flow.Flow

@Dao
interface BookingDao {

    @Insert
    suspend fun insertBooking(booking: BookingEntity): Long

    // Shows pending bookings that are waiting for Accept or Cancel
    @Query("""
        SELECT * FROM bookings
        WHERE status = 'PENDING'
        ORDER BY bookingDate DESC
    """)
    fun getPendingBookings(): Flow<List<BookingEntity>>

    // Shows pending bookings together with their book details
    @Transaction
    @Query("""
        SELECT * FROM bookings
        WHERE status = 'PENDING'
        ORDER BY bookingDate DESC
    """)
    fun getPendingBookingsWithBooks(): Flow<List<BookingWithBook>>

    // Shows accepted/reserved books
    @Query("""
        SELECT * FROM bookings
        WHERE status = 'RESERVED'
        ORDER BY returnDeadline ASC
    """)
    fun getReservedBookings(): Flow<List<BookingEntity>>

    // Shows accepted/reserved books together with book details
    @Transaction
    @Query("""
        SELECT * FROM bookings
        WHERE status = 'RESERVED'
        ORDER BY returnDeadline ASC
    """)
    fun getReservedBookingsWithBooks(): Flow<List<BookingWithBook>>

    @Query("""
        SELECT * FROM bookings
        WHERE bookingId = :bookingId
        LIMIT 1
    """)
    suspend fun getBookingById(bookingId: Int): BookingEntity?

    // Renew a reserved book
    @Query("""
        UPDATE bookings
        SET returnDeadline = :newDeadline
        WHERE bookingId = :bookingId
        AND status = 'RESERVED'
    """)
    suspend fun renewBooking(
        bookingId: Int,
        newDeadline: Long
    )

    // Used for Accept or Cancel
    @Query("""
        UPDATE bookings
        SET status = :status
        WHERE bookingId = :bookingId
    """)
    suspend fun updateStatus(
        bookingId: Int,
        status: BookingStatus
    )

    // Return a book
    @Query("""
        UPDATE bookings
        SET status = 'RETURNED'
        WHERE bookingId = :bookingId
        AND status = 'RESERVED'
    """)
    suspend fun returnBooking(bookingId: Int)

    @Query("""
        DELETE FROM bookings
        WHERE bookingId = :bookingId
        AND status = 'PENDING'
    """)
    suspend fun cancelPendingBooking(bookingId: Int)

    @Query("DELETE FROM bookings")
    suspend fun deleteAllBookings()
}