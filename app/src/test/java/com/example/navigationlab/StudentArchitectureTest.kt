package com.example.navigationlab

import com.example.navigationlab.data.model.Student
import com.example.navigationlab.data.repository.DefaultStudentRepository
import com.example.navigationlab.data.repository.StudentRepository
import com.example.navigationlab.data.source.StudentLocalDataSource
import com.example.navigationlab.ui.detail.DetailViewModel
import com.example.navigationlab.ui.home.HomeViewModel
import org.junit.Assert.*
import org.junit.Test

class StudentArchitectureTest {
    @Test fun sourceContainsOriginalStudentsAndTwoAdditionalUniqueStudents() {
        val students = StudentLocalDataSource().getStudents()
        assertEquals(listOf(2026, 2027, 2028, 2029, 2030), students.map { it.id })
        assertEquals(students.size, students.map { it.id }.distinct().size)
        assertTrue(students.all { it.name.isNotBlank() && it.program.isNotBlank() })
    }

    @Test fun sourceReturnsKnownStudentAndNullForMissingId() {
        val source = StudentLocalDataSource()
        assertEquals("Alya Putri", source.getStudentById(2026)?.name)
        assertNull(source.getStudentById(9999))
    }

    @Test fun repositoryListAndDetailReturnConsistentData() {
        val repository = DefaultStudentRepository(StudentLocalDataSource())
        repository.getStudents().forEach { student ->
            assertEquals(student, repository.getStudentById(student.id))
        }
        assertNull(repository.getStudentById(-1))
    }

    @Test fun homeObtainsItsListThroughInjectedRepository() {
        val fake = FakeStudentRepository(listOf(Student(42, "Uji", "Informatika", 2024)))
        val home = HomeViewModel(fake)
        assertEquals(fake.records, home.uiState.students)
        assertEquals(1, fake.listCalls)
        assertTrue(fake.requestedIds.isEmpty())
    }

    @Test fun homeCanRepresentAnEmptyRepositoryAndReloadIt() {
        val fake = FakeStudentRepository(emptyList())
        val home = HomeViewModel(fake)
        assertTrue(home.uiState.students.isEmpty())
        fake.records = listOf(Student(77, "Baru", "Informatika", 2026))
        home.loadStudents()
        assertEquals(fake.records, home.uiState.students)
        assertEquals(2, fake.listCalls)
    }

    @Test fun detailUsesRequestedIdAndExposesRepositoryResult() {
        val student = Student(42, "Data dari fake", "Program Uji", 2024)
        val fake = FakeStudentRepository(listOf(student))
        val detail = DetailViewModel(fake)
        detail.loadStudent(42)
        assertEquals(listOf(42), fake.requestedIds)
        assertEquals(student, detail.uiState.student)
        assertFalse(detail.uiState.isLoading)
        assertNull(detail.uiState.errorMessage)
    }

    @Test fun missingIdProducesInformativeErrorWithoutStudent() {
        val detail = DetailViewModel(FakeStudentRepository(emptyList()))
        detail.loadStudent(9999)
        assertNull(detail.uiState.student)
        assertFalse(detail.uiState.isLoading)
        assertEquals("Student ID 9999 tidak ditemukan", detail.uiState.errorMessage)
    }

    @Test fun changingIdClearsOldErrorAndThenOldStudent() {
        val student = Student(42, "Valid", "Informatika", 2026)
        val detail = DetailViewModel(FakeStudentRepository(listOf(student)))
        detail.loadStudent(-1)
        detail.loadStudent(42)
        assertEquals(student, detail.uiState.student)
        assertNull(detail.uiState.errorMessage)
        detail.loadStudent(99)
        assertNull(detail.uiState.student)
        assertTrue(detail.uiState.errorMessage!!.contains("99"))
    }

    @Test fun repeatedLoadOfSameIdDoesNotFetchAgain() {
        val fake = FakeStudentRepository(listOf(Student(42, "Valid", "Informatika", 2026)))
        val detail = DetailViewModel(fake)
        detail.loadStudent(42)
        detail.loadStudent(42)
        assertEquals(listOf(42), fake.requestedIds)
    }

    private class FakeStudentRepository(var records: List<Student>) : StudentRepository {
        var listCalls = 0
        val requestedIds = mutableListOf<Int>()
        override fun getStudents(): List<Student> {
            listCalls++
            return records
        }
        override fun getStudentById(id: Int): Student? {
            requestedIds.add(id)
            return records.find { it.id == id }
        }
    }
}
