package com.example.salle.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SalleViewModel: ViewModel(){
    private val _uiState = MutableStateFlow(SalleUiState())
    val uiState: StateFlow<SalleUiState> = _uiState
}
