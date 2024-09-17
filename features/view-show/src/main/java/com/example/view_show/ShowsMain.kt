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
import com.example.shows_data.model.ShowsUi

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
    when (val currentState = state) {
        is State.Success -> ShowsSuccessContent(currentState.shows)
        is State.Error -> ShowsErrorContent(currentState.shows)
        is State.Loading -> ShowsLoadingContent(currentState.shows)
        State.None -> ShowsEmptyContent()
    }
}

@Composable
fun ShowsLoadingContent(shows: List<ShowsUi>?) {
    Log.d("MyLog", "LoadingContent")
    Column {
        Text(text = "ShowsLoadingContent", color = Color.Yellow)
        if (shows != null) {
            ShowsSuccessContent(shows = shows)
        } else {
            Box(modifier = Modifier.padding(16.dp).fillMaxWidth(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun ShowsErrorContent(shows: List<ShowsUi>?) {
    Log.d("MyLog", "ShowsErrorContent")
    Column {
        Text(text = "ShowsErrorContent", color = MaterialTheme.colorScheme.onError)
        if (shows != null) {
            ShowsSuccessContent(shows = shows)
        } else {
            Box(modifier = Modifier.padding(16.dp).fillMaxWidth(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun ShowsEmptyContent() {
    Log.d("MyLog", "ShowsEmptyContent")
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Text(text = "Empty shows content", color = Color.Green)
    }
}

@Composable
private fun ShowsSuccessContent(shows: List<ShowsUi>) {
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
internal fun ShowItem(show: ShowsUi) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = show.name, style = MaterialTheme.typography.titleMedium, color = Color.White)
        Text(text = show.language, style = MaterialTheme.typography.titleSmall, color = Color.White)
        Text(text = show.status, style = MaterialTheme.typography.bodyMedium, color = Color.White)
    }

}

