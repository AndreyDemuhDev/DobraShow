package com.example.dobrashow.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.dobrashow.screens.show_details.ShowPeoplesListUiState
import com.example.dobrashow.screens.show_details.ShowPeoplesState
import com.example.dobrashow.screens.show_details.ShowSeasonsListUiState
import com.example.dobrashow.screens.show_details.ShowSeasonsState
import com.example.uikit.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoBottomSheet(
    peoplesShow: ShowPeoplesListUiState,
    seasonsShow: ShowSeasonsListUiState,
    onClickPerson: (Int) -> Unit,
    onClickSeason: (Int) -> Unit,
    sheetState: SheetState,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = { onDismiss() },
        containerColor = com.example.uikit.AppTheme.colorScheme.background,
        modifier = Modifier.wrapContentHeight()

    ) {
        Column() {
            ShowPeoplesState(
                peoplesShow = peoplesShow,
                onClickPerson = onClickPerson
            )
            ShowSeasonsState(
                seasonsShow = seasonsShow,
                onClickSeason = onClickSeason,
            )
        }
    }
}