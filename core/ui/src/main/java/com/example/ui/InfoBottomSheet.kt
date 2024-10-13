package com.example.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.design.theme.AppTheme
import com.example.domain.model.CastShowUi
import com.example.domain.model.CrewShowUi


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoBottomSheet(
    listCast: List<CastShowUi>,
    listCrew: List<CrewShowUi>,
//    seasonsShow: ShowSeasonsListUiState,
    onClickPerson: (Int) -> Unit,
    onClickSeason: (Int) -> Unit,
    sheetState: SheetState,
    onDismiss: () -> Unit,
) {
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = { onDismiss() },
        containerColor = AppTheme.colorScheme.background,
        modifier = Modifier.wrapContentHeight()

    ) {
        Column {
            CastAndCrewShowPager(
                cast = listCast,
                crew = listCrew,
                onClickPerson = onClickPerson,
                modifier = Modifier.padding(
                    horizontal = AppTheme.size.dp8,
                    vertical = AppTheme.size.dp4
                )
            )
//            ShowSeasonsState(
//                seasonsShow = seasonsShow,
//                onClickSeason = onClickSeason,
//            )
        }
    }
}