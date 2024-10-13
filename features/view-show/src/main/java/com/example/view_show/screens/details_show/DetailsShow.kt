package com.example.view_show.screens.details_show

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.design.theme.AppTheme
import com.example.domain.model.ShowsUi

//


@Composable
fun ShowDetailsMainScreen(modifier: Modifier = Modifier) {
    ShowDetailsScreen(modifier = modifier)
}


@Composable
internal fun ShowDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailsShowViewModel = hiltViewModel(),
) {
    Log.d("MyLog", "ShowsScreen")
    val stateShowInformation by viewModel.showDetailState.collectAsState()

    if (stateShowInformation != StateShow.None) {
        DetailsShowStateContent(showState = stateShowInformation, modifier = modifier)
    }
}


@Composable
private fun DetailsShowStateContent(
    showState: StateShow,
    modifier: Modifier = Modifier
) {
    if (showState is StateShow.Error) {
        ErrorDetailShowContent(stateShow = showState, modifier = modifier)
    }
    if (showState is StateShow.Loading) {
        LoadingStateContent(
            modifier = modifier
        )
    }
    if (showState.show != null) {
        DetailShowInformation(showState = showState.show, modifier = modifier)
    }
}

@Composable
fun DetailShowInformation(
    showState: ShowsUi,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier
        .fillMaxSize()
        .background(Color.Gray)) {
        Text(text = showState.name )
        Text(text = showState.summary )
    }
}


@Composable
private fun ErrorDetailShowContent(stateShow: StateShow.Error, modifier: Modifier = Modifier) {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "ShowsErrorContent", color = AppTheme.colorScheme.error)
        }
    }
}


@Composable
fun LoadingStateContent(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(AppTheme.size.dp24)
        )
    }
}
