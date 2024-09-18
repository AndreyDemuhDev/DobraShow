package com.example.dobrashow.ui.components

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
import com.example.dobrashow.R
import com.example.uikit.AppTheme

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
                        tint = com.example.uikit.AppTheme.colorScheme.text
                    )
                }
            }
            Text(text = title,
                color = com.example.uikit.AppTheme.colorScheme.text,
                style = com.example.uikit.AppTheme.typography.titleLarge)
        }
        HorizontalDivider(
            thickness = com.example.uikit.AppTheme.size.dp2,
            color = com.example.uikit.AppTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = com.example.uikit.AppTheme.size.dp8)
        )
    }
}