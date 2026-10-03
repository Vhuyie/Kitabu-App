package com.example.kitabu.data.database

import androidx.room.TypeConverter
import com.example.kitabu.data.entity.BookingStatus

class Converters {

    @TypeConverter
    fun fromBookingStatus(status: BookingStatus): String {
        return status.name
    }

    @TypeConverter
    fun toBookingStatus(value: String): BookingStatus {
        return BookingStatus.valueOf(value)
    }
}