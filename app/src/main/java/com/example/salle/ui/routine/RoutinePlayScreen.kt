package com.example.salle.ui.routine

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.salle.R
import com.example.salle.ui.AppViewModelProvider
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoutinePlayScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RoutinePlayViewModel = viewModel(factory = AppViewModelProvider.Factory)
){
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = viewModel.routineUiState.routineName,
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
    ) { innerPadding ->
        RoutinePlayScreenBody(
            viewModel = viewModel,
            onValueChange = {id, value -> viewModel.updateUiState(id, value) },
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.saveHistory()
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

@Composable
fun RoutinePlayScreenBody(
    viewModel: RoutinePlayViewModel,
    onValueChange: (Int, Boolean) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large)),
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium))
    ) {
        RoutinePLayInputForm(
            viewModel = viewModel,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            enabled = viewModel.routinePlayUiState.isRoutineCompleted,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Save")
        }
    }
}

@Composable
fun RoutinePLayInputForm(
    viewModel: RoutinePlayViewModel,
    onValueChange: (Int, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ){
        var checkboxIndex = 0
        viewModel.routineUiState.exercises.forEach{ exercise ->
            ExerciseRow(
                exercise = exercise,
                checkboxes = List(exercise.sets.toIntOrNull() ?: 0) { index: Int ->  checkboxIndex + index},
                onValueChange = onValueChange,
                viewModel = viewModel,
                modifier = Modifier.fillMaxWidth()
            )

            checkboxIndex += exercise.sets.toIntOrNull() ?: 0
        }
    }
}

@Composable
fun ExerciseRow(
    exercise: ExerciseInfo,
    checkboxes: List<Int>,
    viewModel: RoutinePlayViewModel,
    modifier: Modifier = Modifier,
    onValueChange: (Int, Boolean) -> Unit
) {
    Text(
        text = exercise.name,
        style = MaterialTheme.typography.bodyLarge,
    )
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Sets: " + exercise.sets,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "Reps: " + exercise.reps,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = "Weight: " + exercise.weight,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.weight(1f)
        )

    }
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        checkboxes.forEach {
            CheckboxItem(
                index = it,
                onValueChange = onValueChange,
                checked = viewModel.routinePlayUiState.checkboxes.get(it),
            )
        }
    }
}

@Composable
fun CheckboxItem(
    index: Int,
    checked: Boolean,
    onValueChange: (Int, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Checkbox(
        checked = checked,
        onCheckedChange = {onValueChange(index, it)},
        modifier = modifier
    )
}
