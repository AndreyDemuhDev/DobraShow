package com.example.view_show

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uikit.AppTheme

@Composable
fun ShowsMainScreen(modifier: Modifier = Modifier) {
    ShowsScreen(viewModel = viewModel())
}

@Composable
internal fun ShowsScreen(
    viewModel: ShowViewModel,
) {
    Log.d("MyLog", "ShowsScreen")
    val state by viewModel.state.collectAsState()
    val currentState = state

    if (state != State.None) {
        ShowsContent(currentState)
    }

}

@Composable
private fun ShowsContent(currentState: State) {
    if (currentState is State.Error) {
        ErrorContent(currentState)
    }
    if (currentState is State.Loading) {
        ProgressIndicator(currentState)
    }
    if (currentState.shows != null) {
        ListShows(shows = currentState.shows)
    }
}

@Composable
private fun ErrorContent(state: State.Error) {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "ShowsErrorContent", color = AppTheme.colorScheme.error)
        }
    }
}

@Composable
private fun ProgressIndicator(state: State.Loading) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = AppTheme.colorScheme.primary,
            strokeWidth = 4.dp
        )
    }
}

@Composable
private fun ListShows(shows: List<ShowsUI>) {
    Log.d("MyLog", "ShowsSuccessContent")
    LazyColumn {
        items(shows) { show ->
            key(show.id) {
                ShowItem(show)
            }
        }
    }

}

@Composable
internal fun ShowItem(show: ShowsUI) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = show.name, style = MaterialTheme.typography.titleMedium, color = Color.White)
        Text(text = show.language, style = MaterialTheme.typography.titleSmall, color = Color.White)
        Text(text = show.status, style = MaterialTheme.typography.bodyMedium, color = Color.White)
    }

}

