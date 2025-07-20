package com.example.salle.ui.routine

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.salle.R
import com.example.salle.ui.AppViewModelProvider
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoutineEntryScreen(
    onBackClick: () -> Unit,
    viewModel: AddRoutineViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier,
) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
               title = {
                   Text(
                       text = "Add Routine",
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

@Composable
fun RoutineEntryScreenBody(
    routineUiState: RoutineUiState,
    onRoutineNameValueChange: (String) -> Unit,
    onExerciceValueChange: (ExerciseInfo) -> Unit,
    onAddButtonClick: () -> Unit,
    onDeleteButtonClick: () -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large)),
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium))
    ) {
        RoutineInputForm(
            routineUiState = routineUiState,
            onRoutineNameValueChange = onRoutineNameValueChange,
            onExerciceValueChange = onExerciceValueChange,
            onAddButtonClick = onAddButtonClick,
            onDeleteButtonClick = onDeleteButtonClick,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            enabled = routineUiState.isEntryValid,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Save")
        }
    }
}

@Composable
fun RoutineInputForm(
    modifier: Modifier,
    enabled: Boolean = true,
    routineUiState: RoutineUiState,
    onRoutineNameValueChange: (String) -> Unit,
    onExerciceValueChange: (ExerciseInfo) -> Unit,
    onAddButtonClick: () -> Unit,
    onDeleteButtonClick: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        OutlinedTextField(
            value = routineUiState.routineName,
            onValueChange = { onRoutineNameValueChange(it) },
            label = { Text("Routine Name") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        routineUiState.exercises.forEach { exercice ->
            ExerciceForm(
                exerciceInfo = exercice,
                onExerciceValueChange = onExerciceValueChange,
                modifier = Modifier.fillMaxWidth(),
            )
        }

        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = { onAddButtonClick() },
                enabled = routineUiState.canAddExercises
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    tint = MaterialTheme.colorScheme.primary,
                )
            }

            IconButton(
                onClick = { onDeleteButtonClick() },
                enabled = routineUiState.canDeleteExercises,
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.Red,
                )
            }
        }

    }
}

@Composable
fun ExerciceForm(
    exerciceInfo: ExerciseInfo,
    onExerciceValueChange: (ExerciseInfo) -> Unit,
    modifier: Modifier = Modifier,
){
    Row(
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ){

        OutlinedTextField(
            value = exerciceInfo.name,
            onValueChange = { onExerciceValueChange(exerciceInfo.copy(name = it)) },
            label = { Text("Exercice") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            enabled = true,
            singleLine = true,
            modifier = Modifier.weight(2f)
        )
        OutlinedTextField(
            value = exerciceInfo.sets,
            onValueChange = { onExerciceValueChange(exerciceInfo.copy(sets = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text("S") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            enabled = true,
            singleLine = true,
            modifier = Modifier.weight(1f)
        )
        OutlinedTextField(
            value = exerciceInfo.reps,
            onValueChange = { onExerciceValueChange(exerciceInfo.copy(reps = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text("R") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            enabled = true,
            singleLine = true,
            modifier = Modifier.weight(1f)
        )
        OutlinedTextField(
            value = exerciceInfo.weight,
            onValueChange = { onExerciceValueChange(exerciceInfo.copy(weight = it))},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text("W") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            enabled = true,
            singleLine = true,
            modifier = Modifier.weight(1f)
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun AddRoutineScreenPreview() {
//    AppTheme (dynamicColor = false){
//        AddRoutineScreen(
//            onBackClick = {},
//            onNavigateUp = {},
//            modifier = Modifier.fillMaxSize()
//        )
//    }
//}