package com.example.dobrashow.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.design.theme.AppTheme

//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun InfoBottomSheet(
//    peoplesShow: ShowPeoplesListUiState,
//    seasonsShow: ShowSeasonsListUiState,
//    onClickPerson: (Int) -> Unit,
//    onClickSeason: (Int) -> Unit,
//    sheetState: SheetState,
//    onDismiss: () -> Unit,
//) {
//    ModalBottomSheet(
//        sheetState = sheetState,
//        onDismissRequest = { onDismiss() },
//        containerColor = AppTheme.colorScheme.background,
//        modifier = Modifier.wrapContentHeight()
//
//    ) {
//        Column {
//            ShowPeoplesState(
//                peoplesShow = peoplesShow,
//                onClickPerson = onClickPerson
//            )
//            ShowSeasonsState(
//                seasonsShow = seasonsShow,
//                onClickSeason = onClickSeason,
//            )
//        }
//    }
//}
