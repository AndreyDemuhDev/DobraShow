package com.example.view_show

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
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
    val state by viewModel.state.collectAsState()
    when (val currentState = state) {
        is State.Success -> ShowsContent(currentState.shows)
        is State.Error -> Text(text = "Error")
        is State.Loading -> Text(text = "Loading")
        State.None -> ShowsEmpty()
    }
}

@Composable
fun ShowsEmpty() {

}

@Composable
private fun ShowsContent(shows: List<ShowsUi>) {
    Log.d("MyLog", "Content $shows")
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

