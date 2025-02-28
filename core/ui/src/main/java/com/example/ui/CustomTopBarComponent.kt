package com.example.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.design.theme.AppTheme


@Composable
fun CustomTopBarComponent(
    title: String,
    modifier: Modifier = Modifier,
    onClickBack: (() -> Unit)? = null,
) {
    Column(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (onClickBack != null) {
                IconButton(onClick = { onClickBack() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_back),
                        contentDescription = "arrow_back",
                        tint = AppTheme.colorScheme.text
                    )
                }
            }
            Text(
                text = title,
                color = AppTheme.colorScheme.text,
                style = AppTheme.typography.titleLarge
            )
        }
        HorizontalDivider(
            thickness = AppTheme.size.dp2,
            color = AppTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = AppTheme.size.dp8)
        )
    }
}