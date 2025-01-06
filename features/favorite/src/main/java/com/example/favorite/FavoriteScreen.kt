package com.example.favorite

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.design.theme.AppTheme
import com.example.ui.CustomTopBarComponent

@Composable
fun FavoriteMainScreen(
    onClickShow: (Int) -> Unit,
    onClickDelete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    FavoriteScreen(
        onClickShow = onClickShow,
        onClickDelete = onClickDelete,
        modifier = modifier
    )

}

@Composable
internal fun FavoriteScreen(
    onClickShow: (Int) -> Unit,
    onClickDelete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = AppTheme.size.dp16)
    ) {
        CustomTopBarComponent(title = stringResource(id = R.string.favorite_shows_tob_bar))
    }
}