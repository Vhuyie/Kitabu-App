package com.example.kitabu.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kitabu.data.entity.StudentEntity

@Dao
interface StudentDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertStudent(
        student: StudentEntity
    ): Long

    @Query("""
        SELECT * FROM students
        WHERE username = :username
        LIMIT 1
    """)
    suspend fun getStudentByUsername(
        username: String
    ): StudentEntity?

    @Query("""
        SELECT * FROM students
        WHERE studentNumber = :studentNumber
        LIMIT 1
    """)
    suspend fun getStudentByStudentNumber(
        studentNumber: String
    ): StudentEntity?

    @Query("""
        SELECT * FROM students
        WHERE email = :email
        LIMIT 1
    """)
    suspend fun getStudentByEmail(
        email: String
    ): StudentEntity?

    @Query("""
        SELECT * FROM students
        WHERE username = :username
        AND password = :password
        LIMIT 1
    """)
    suspend fun loginStudent(
        username: String,
        password: String
    ): StudentEntity?
}