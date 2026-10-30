package com.example.navigationlab.data.source

import com.example.navigationlab.data.model.Student

/** Sumber data sementara di memori untuk Praktikum 06. */
class StudentLocalDataSource {
    private val students = listOf(
        Student(2026, "Alya Putri", "Teknik Informatika", 2026),
        Student(2027, "Bima Pratama", "Teknik Informatika", 2026),
        Student(2028, "Citra Lestari", "Teknik Informatika", 2026),
        Student(2029, "Dimas Saputra", "Teknik Informatika", 2026),
        Student(2030, "Eka Maharani", "Teknik Informatika", 2026)
    )

    fun getStudents(): List<Student> = students.toList()

    fun getStudentById(id: Int): Student? = students.find { it.id == id }
}
