package com.example.studentdatabaseapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface StudentDao {
    @Insert
    suspend fun insert(student: Student)

    @Query("SELECT * FROM students WHERE id = :id")
    suspend fun getStudentById(id: Int): Student?

    @Query("DELETE FROM students")
    suspend fun deleteAll()

    @Query("SELECT * FROM students")
    suspend fun getAllStudents(): List<Student>
}