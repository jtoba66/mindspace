package com.mindspace.app.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindspace.app.data.model.PlanResponse
import com.mindspace.app.data.repository.SessionRepository
import kotlinx.coroutines.launch

sealed class SessionUiState {
    object Idle : SessionUiState()
    object Processing : SessionUiState()
    data class Success(val response: PlanResponse) : SessionUiState()
    data class Error(val message: String) : SessionUiState()
}

class SessionViewModel(private val repository: SessionRepository) : ViewModel() {
    
    var uiState: SessionUiState by mutableStateOf(SessionUiState.Idle)
        private set

    var journeyState: List<PlanResponse> by mutableStateOf(emptyList())
        private set

    var currentInput: String by mutableStateOf("")
    var isPrivateMode: Boolean by mutableStateOf(false)
    var isDarkMode: Boolean by mutableStateOf(false)
    var selectedEnergy: String? by mutableStateOf(null)

    fun submitBrainDump() {
        if (currentInput.isBlank()) return

        uiState = SessionUiState.Processing
        
        viewModelScope.launch {
            try {
                val response = repository.processBrainDump(
                    input = currentInput,
                    isPrivate = isPrivateMode,
                    energy = selectedEnergy
                )

                if (response.isSuccessful && response.body() != null) {
                    uiState = SessionUiState.Success(response.body()!!)
                    fetchJourney() // Refresh journey after new entry
                } else {
                    uiState = SessionUiState.Error("Something went wrong on our end. Please try again.")
                }
            } catch (e: Exception) {
                uiState = SessionUiState.Error("Could not reach the server. Make sure it is running.")
            }
        }
    }

    fun fetchJourney() {
        viewModelScope.launch {
            try {
                val response = repository.getJourney()
                if (response.isSuccessful && response.body() != null) {
                    journeyState = response.body()!!
                }
            } catch (e: Exception) {
                // Silently fail or log
            }
        }
    }

    fun deleteData() {
        // Logic for full wipe - we would normally have an API for this
        journeyState = emptyList()
    }

    fun reset() {
        uiState = SessionUiState.Idle
        currentInput = ""
        selectedEnergy = null
    }
}
