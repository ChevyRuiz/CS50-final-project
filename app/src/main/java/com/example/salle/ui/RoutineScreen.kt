package com.example.salle.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.salle.ui.theme.AppTheme

@Composable
fun RoutineScreen(
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = "Routines",
            style = MaterialTheme.typography.displayLarge
        )
        Button(
            onClick = onButtonClick
        ) {
            Text(
                text = "Go to Add Routines",
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RoutineScreenPreview() {
    AppTheme (dynamicColor = false){
        RoutineScreen(
            onButtonClick = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}