package com.example.kitabu.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Transaction
import com.example.kitabu.data.entity.BookingEntity
import com.example.kitabu.data.entity.BookingStatus
import com.example.kitabu.data.entity.BookingWithBook
import kotlinx.coroutines.flow.Flow

@Dao
interface BookingDao {

    @Insert
    suspend fun insertBooking(booking: BookingEntity): Long

    @Query("""
        SELECT * FROM bookings
        WHERE status = 'ACTIVE'
        ORDER BY returnDeadline ASC
    """)
    fun getActiveBookings(): Flow<List<BookingEntity>>

    @Transaction
    @Query("""
        SELECT * FROM bookings
        WHERE status = 'ACTIVE'
        ORDER BY returnDeadline ASC
    """)
    fun getActiveBookingsWithBooks(): Flow<List<BookingWithBook>>

    @Query("""
        SELECT * FROM bookings
        WHERE bookingId = :bookingId
        LIMIT 1
    """)
    suspend fun getBookingById(bookingId: Int): BookingEntity?

    @Query("""
        UPDATE bookings
        SET returnDeadline = :newDeadline
        WHERE bookingId = :bookingId
    """)
    suspend fun renewBooking(
        bookingId: Int,
        newDeadline: Long
    )

    @Query("""
        UPDATE bookings
        SET status = :status
        WHERE bookingId = :bookingId
    """)
    suspend fun updateStatus(
        bookingId: Int,
        status: BookingStatus
    )

    @Update
    suspend fun updateBooking(booking: BookingEntity)

    @Query("""
        DELETE FROM bookings
        WHERE bookingId = :bookingId
        AND status = 'PENDING'
    """)
    suspend fun cancelPendingBooking(bookingId: Int)

    @Query("DELETE FROM bookings")
    suspend fun deleteAllBookings()
}