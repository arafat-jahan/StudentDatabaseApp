package com.example.studentdatabaseapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class Student(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var firstName: String,
    var lastName: String,
    var rollNumber: String
)
