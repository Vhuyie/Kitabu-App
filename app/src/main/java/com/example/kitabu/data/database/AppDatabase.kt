package com.example.kitabu.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.kitabu.data.dao.BookDao
import com.example.kitabu.data.dao.BookingDao
import com.example.kitabu.data.dao.StudentDao
import com.example.kitabu.data.entity.BookEntity
import com.example.kitabu.data.entity.BookingEntity
import com.example.kitabu.data.entity.StudentEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        BookEntity::class,
        BookingEntity::class,
        StudentEntity::class
    ],
    version = 3,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun bookDao(): BookDao

    abstract fun bookingDao(): BookingDao

    abstract fun studentDao(): StudentDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        /**
         * Migration from database version 1 to version 2.
         *
         * Adds the students table without deleting
         * existing books or bookings.
         */
        private val MIGRATION_1_2 = object : Migration(1, 2) {

            override fun migrate(
                database: SupportSQLiteDatabase
            ) {

                database.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS students (
                        studentId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        fullName TEXT NOT NULL,
                        studentNumber TEXT NOT NULL,
                        email TEXT NOT NULL,
                        username TEXT NOT NULL,
                        password TEXT NOT NULL
                    )
                    """.trimIndent()
                )

                database.execSQL(
                    """
                    CREATE UNIQUE INDEX IF NOT EXISTS
                    index_students_studentNumber
                    ON students(studentNumber)
                    """.trimIndent()
                )

                database.execSQL(
                    """
                    CREATE UNIQUE INDEX IF NOT EXISTS
                    index_students_username
                    ON students(username)
                    """.trimIndent()
                )

                database.execSQL(
                    """
                    CREATE UNIQUE INDEX IF NOT EXISTS
                    index_students_email
                    ON students(email)
                    """.trimIndent()
                )
            }
        }

        /**
         * Migration from database version 2 to version 3.
         *
         * Adds the imageResId column to the books table.
         */
        private val MIGRATION_2_3 = object : Migration(2, 3) {

            override fun migrate(
                database: SupportSQLiteDatabase
            ) {

                database.execSQL(
                    """
                    ALTER TABLE books
                    ADD COLUMN imageResId INTEGER NOT NULL DEFAULT 0
                    """.trimIndent()
                )
            }
        }

        fun getDatabase(
            context: Context
        ): AppDatabase {

            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "kitabu_database"
                )
                    .addMigrations(
                        MIGRATION_1_2,
                        MIGRATION_2_3
                    )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance

                CoroutineScope(Dispatchers.IO).launch {
                    seedDatabase(instance)
                }

                instance
            }
        }
    }
}