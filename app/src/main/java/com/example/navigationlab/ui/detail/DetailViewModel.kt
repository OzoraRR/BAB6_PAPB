package com.example.navigationlab.ui.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.navigationlab.data.repository.StudentRepository

class DetailViewModel(private val repository: StudentRepository) : ViewModel() {
    var uiState by mutableStateOf(DetailUiState())
        private set

    private var loadedStudentId: Int? = null

    fun loadStudent(id: Int) {
        // Hindari pemuatan ulang saat destination yang sama direkomposisi/dirotasi.
        if (loadedStudentId == id) return
        val student = repository.getStudentById(id)
        uiState = if (student != null) {
            DetailUiState(student = student, isLoading = false)
        } else {
            DetailUiState(
                isLoading = false,
                errorMessage = "Student ID $id tidak ditemukan"
            )
        }
        loadedStudentId = id
    }

    companion object {
        fun factory(repository: StudentRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    require(modelClass.isAssignableFrom(DetailViewModel::class.java))
                    @Suppress("UNCHECKED_CAST")
                    return DetailViewModel(repository) as T
                }
            }
    }
}
