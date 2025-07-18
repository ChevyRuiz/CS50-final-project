package com.example.salle.ui.routine

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.salle.ui.AppViewModelProvider

@Composable
fun RoutineEditScreen(
    modifier: Modifier = Modifier,
    viewModel: RoutineEditViewModel = viewModel(factory = AppViewModelProvider.Factory),
){
    Text(viewModel.showRoutineId())
}