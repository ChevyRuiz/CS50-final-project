package com.example.salle.ui.routine

import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.salle.ui.AppViewModelProvider
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoutineEditScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RoutineEditViewModel = viewModel(factory = AppViewModelProvider.Factory),
){
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Edit Routine",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick
                    ) {
                        Icon(
                            imageVector =  Icons. AutoMirrored. Filled. ArrowBack,
                            contentDescription = "back button"
                        )
                    }
                },
            )
        },

        modifier = modifier
    ) {
            innerPadding ->
        RoutineEntryScreenBody(
            routineUiState = viewModel.routineUiState,
            onRoutineNameValueChange = {name -> viewModel.updateUiState(name) },
            onExerciceValueChange = {exerciceInfo -> viewModel.updateUiState(exerciceInfo) },
            onAddButtonClick = { viewModel.updateUiStateAddExercice() },
            onDeleteButtonClick = { viewModel.updateUiStateDeleteExercice() },
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.saveRoutineAndExercises()
                    onBackClick()
                }
            },
            modifier = Modifier
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding()
                )
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}