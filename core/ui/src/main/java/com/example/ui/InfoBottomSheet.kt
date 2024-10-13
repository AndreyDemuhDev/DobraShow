package com.example.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.design.theme.AppTheme
import com.example.domain.model.CastShowUi
import com.example.domain.model.CrewShowUi
import com.example.domain.model.SeasonsShowUi


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoBottomSheet(
    listCast: List<CastShowUi>,
    listCrew: List<CrewShowUi>,
    listSeasonsShow: List<SeasonsShowUi>,
    onClickPerson: (Int) -> Unit,
    onClickSeason: (Int) -> Unit,
    sheetState: SheetState,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
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
            LazyRow(modifier = modifier.padding(horizontal = AppTheme.size.dp12)) {
                items(listSeasonsShow) { seasonItem ->
                    SeasonsItemCard(
                        seasonItem = seasonItem,
                        onClickSeason = onClickSeason,
                        modifier = Modifier.padding(
                            horizontal = AppTheme.size.dp4,
                            vertical = AppTheme.size.dp2
                        )
                    )
                }
            }
        }
    }
}
