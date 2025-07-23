package com.example.salle.ui.activity

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.salle.R
import com.example.salle.data.model.History
import com.example.salle.ui.AppViewModelProvider
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityScreen(
    modifier: Modifier = Modifier,
    viewModel: ActivityViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val activityUiState by viewModel.activityUiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Activity",
                        style = MaterialTheme.typography.displayMedium
                    )
                }
            )
        },
        modifier = modifier
    ) {
        ActivityScreenBody(
            daysCounter = activityUiState.daysCounter,
            routinesAndDates = activityUiState.routinesWithDates,
            modifier = Modifier.padding(
                top = it.calculateTopPadding(),
                start = it.calculateStartPadding(layoutDirection = LocalLayoutDirection.current),
                end = it.calculateEndPadding(layoutDirection = LocalLayoutDirection.current)
            )
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ActivityScreenBody(daysCounter: Int, routinesAndDates: List<History>, modifier: Modifier) {
        LazyColumn(
            modifier = modifier
                .padding(
                    horizontal = dimensionResource(id = R.dimen.padding_small)
                ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth().padding(top = dimensionResource(R.dimen.padding_extra_large))
                ) {
                    Text(
                        text = "You have exercised on ${daysCounter} days in ${routinesAndDates.firstOrNull()?.timeStamp?.year ?: 0}",
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center
                    )
                }
            }
            items(routinesAndDates){ history ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(id = R.dimen.padding_small)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Text(
                        text ="You completed ${history.routineName} on ${DateTimeFormatter.ofPattern("MMM d, yyyy").format(history.timeStamp)}",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(20.dp)
                    )
                }
            }
        }
}


val fakeHashMap = hashMapOf<String, String>(
    "Routine 1" to "July 19, 2025",
    "Routine 2" to "March 3, 2025",
    "Routine 3" to "September 20, 2025",
)

val daysOfTheYear = List(365) {true}

@Composable
fun DaysGrid360(days: List<Boolean>) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 16.dp), // auto-adjusts column count based on screen width
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        contentPadding = PaddingValues(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 500.dp) // adjust as needed
    ) {
        items(days.size) { index ->
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .background(
                        if (days[index]) Color.Black else Color.LightGray,
                        shape = RectangleShape
                    )
                    .border(1.dp, Color.Black)
            )
        }
    }
}
//@Preview(showBackground = true)
//@Composable
//fun ActivityScreenPreview() {
//    AppTheme (dynamicColor = false){
//        ActivityScreen(
//            routinesAndDates = listOf(
//
//            ),
//            modifier = Modifier.fillMaxSize()
//        )
//    }
//}