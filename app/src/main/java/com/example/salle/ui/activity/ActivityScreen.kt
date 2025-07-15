package com.example.salle.ui.activity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.salle.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityScreen(
    modifier: Modifier = Modifier
) {
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
        Column(
            modifier = Modifier.padding(it)
        ) {

        }
    }
}

@Preview(showBackground = true)
@Composable
fun ActivityScreenPreview() {
    AppTheme (dynamicColor = false){
        ActivityScreen(
            modifier = Modifier.fillMaxSize()
        )
    }
}