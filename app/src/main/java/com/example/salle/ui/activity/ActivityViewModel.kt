package com.example.salle.ui.activity

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salle.data.model.History
import com.example.salle.data.repositories.HistoryRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ActivityViewModel (
    private val historyRepository: HistoryRepository
) : ViewModel() {

    @RequiresApi(Build.VERSION_CODES.O)
    val activityUiState: StateFlow<ActivityUiState> =
        historyRepository.getRoutinesWithDates().map { ActivityUiState(it.take(10), daysCounter = countDays(it)) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = ActivityUiState()
            )

    @RequiresApi(Build.VERSION_CODES.O)
    private fun countDays(routinesWithDates: List<History>): Int {
        val latestDay = routinesWithDates.firstOrNull()?.timeStamp ?: return 0
        return routinesWithDates.count{ it.timeStamp.year == latestDay.year}
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class ActivityUiState (
    val routinesWithDates: List<History> = listOf(),
    val daysCounter: Int = 0
)