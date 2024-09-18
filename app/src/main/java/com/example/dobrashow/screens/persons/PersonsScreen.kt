package com.example.dobrashow.screens.persons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dobrashow.screens.show_details.LoadingStateContent
import com.example.dobrashow.ui.components.CustomTopBarComponent
import com.example.dobrashow.ui.components.PersonCardItem
import com.example.uikit.AppTheme

@Composable
fun PersonsScreen(
    onClickPerson: (Int) -> Unit,
    modifier: Modifier = Modifier,
    personsViewModel: PersonsViewModel = hiltViewModel()
) {

    val viewState by personsViewModel.listPersonsState.collectAsState()

    LaunchedEffect(key1 = Unit, block = { personsViewModel.initialPersonsPage() })

    val scrollState = rememberLazyGridState()
    val fetchNextPage: Boolean by remember {
        derivedStateOf {
            val currentPersonCount = (viewState as? PersonsUiState.Success)?.listPersons?.size
                ?: return@derivedStateOf false
            val lastDisplayedIndexPersons =
                scrollState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                    ?: return@derivedStateOf false
            return@derivedStateOf lastDisplayedIndexPersons >= currentPersonCount - 10
        }
    }

    LaunchedEffect(key1 = fetchNextPage, block = {
        if (fetchNextPage) personsViewModel.fetchNextPage()
    })


    when (val state = viewState) {
        is PersonsUiState.Error -> {
            Text(text = "Error load list persons")
        }

        PersonsUiState.Loading -> {
            LoadingStateContent()
        }

        is PersonsUiState.Success -> {
            SuccessPersonsStateContent(
                scrollState = scrollState,
                personsState = state,
                onClickPerson = onClickPerson
            )
        }
    }

}

@Composable
fun SuccessPersonsStateContent(
    scrollState: LazyGridState,
    personsState: PersonsUiState.Success,
    onClickPerson: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        CustomTopBarComponent(
            title = "All persons",
            modifier = Modifier.padding(horizontal = com.example.uikit.AppTheme.size.dp16)
        )
        LazyVerticalGrid(
            state = scrollState,
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(all = com.example.uikit.AppTheme.size.dp16),
            verticalArrangement = Arrangement.spacedBy(com.example.uikit.AppTheme.size.dp8),
            horizontalArrangement = Arrangement.spacedBy(com.example.uikit.AppTheme.size.dp8),
            content = {
                items(items = personsState.listPersons, key = { it.id }) { person ->
                    PersonCardItem(
                        person = person,
                        onClickPerson = { onClickPerson(person.id.toInt()) },
                    )
                }
            }
        )
    }
}

