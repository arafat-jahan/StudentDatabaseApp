package com.example.studentdatabaseapp
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var firstnameEt: EditText
    private lateinit var lastnameEt: EditText
    private lateinit var rollnoEt: EditText
    private lateinit var searchbyidEt: EditText
    private lateinit var database: StudentDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firstnameEt = findViewById(R.id.firstnameEt)
        lastnameEt = findViewById(R.id.lastnameEt)
        rollnoEt = findViewById(R.id.rollnoEt)
        searchbyidEt = findViewById(R.id.searchbyidEt)
        val saveButton: Button = findViewById(R.id.button)
        val searchButton: Button = findViewById(R.id.searchbt)
        val deleteButton: Button = findViewById(R.id.deletebt)

        database = StudentDatabase.getInstance(this)

        saveButton.setOnClickListener { saveStudent() }
        searchButton.setOnClickListener { searchStudent() }
        deleteButton.setOnClickListener { deleteAllStudents() }
    }

    private fun saveStudent() {
        val firstName = firstnameEt.text.toString()
        val lastName = lastnameEt.text.toString()
        val rollNumber = rollnoEt.text.toString()

        if (firstName.isEmpty() || lastName.isEmpty() || rollNumber.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val student = Student(firstName = firstName, lastName = lastName, rollNumber = rollNumber)

        CoroutineScope(Dispatchers.IO).launch {
            database.studentDao().insert(student)
            withContext(Dispatchers.Main) {
                Toast.makeText(this@MainActivity, "Student saved", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun searchStudent() {
        val id = searchbyidEt.text.toString().toIntOrNull()

        if (id == null) {
            Toast.makeText(this, "Invalid ID", Toast.LENGTH_SHORT).show()
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            val student = database.studentDao().getStudentById(id)
            withContext(Dispatchers.Main) {
                if (student != null) {
                    firstnameEt.setText(student.firstName)
                    lastnameEt.setText(student.lastName)
                    rollnoEt.setText(student.rollNumber)
                } else {
                    Toast.makeText(this@MainActivity, "Student not found", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun deleteAllStudents() {
        CoroutineScope(Dispatchers.IO).launch {
            database.studentDao().deleteAll()
            withContext(Dispatchers.Main) {
                Toast.makeText(this@MainActivity, "All students deleted", Toast.LENGTH_SHORT).show()
            }
        }
    }
}