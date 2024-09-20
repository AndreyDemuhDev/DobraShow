package com.example.dobrashow.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.dobrashow.R

@Composable
@Suppress("LongMethod")
fun SearchFieldComponent(
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier,
    query: String = "",
) {
    var queryShow by remember { mutableStateOf(query) }

    Row(
        modifier = modifier
            .padding(horizontal = com.example.uikit.AppTheme.size.dp24)
            .fillMaxWidth()
            .background(
                color = com.example.uikit.AppTheme.colorScheme.background,
                shape = com.example.uikit.AppTheme.shape.large
            )
            .border(
                width = 1.dp,
                color = com.example.uikit.AppTheme.colorScheme.primary,
                shape = com.example.uikit.AppTheme.shape.large
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = queryShow, onValueChange = { queryShow = it },
            placeholder = {
                Text(
                    text = "Search",
                    style = com.example.uikit.AppTheme.typography.bodyNormal
                )
            },
            singleLine = true,
            maxLines = 1,
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = com.example.uikit.AppTheme.colorScheme.background,
                focusedIndicatorColor = com.example.uikit.AppTheme.colorScheme.background
            ),
            shape = com.example.uikit.AppTheme.shape.large,
            trailingIcon = {
//                if (queryShow.isEmpty()) {
                Image(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "search",
                    modifier = Modifier
                        .padding(end = com.example.uikit.AppTheme.size.dp8)
                        .size(38.dp)
                        .background(
                            color = com.example.uikit.AppTheme.colorScheme.onText,
                            shape = com.example.uikit.AppTheme.shape.large
                        )
                        .padding(all = com.example.uikit.AppTheme.size.dp8)
                        .clickable { onSearch(queryShow) },
                    colorFilter = ColorFilter.tint(com.example.uikit.AppTheme.colorScheme.primary)
                )
//                } else {
//                    Image(
//                        imageVector = Icons.Default.Close,
//                        contentDescription = "search",
//                        modifier = Modifier
//                            .padding(end = 8.dp)
//                            .size(38.dp)
//                            .background(
//                                color = MaterialTheme.colorScheme.secondary,
//                                shape = MaterialTheme.shapes.extraLarge
//                            )
//                            .padding(6.dp)
//                            .clickable { queryShow = "" },
//                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.background)
//                    )
//                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = com.example.uikit.AppTheme.colorScheme.background,
                    shape = com.example.uikit.AppTheme.shape.small
                )
                .width(40.dp)
                .border(
                    width = com.example.uikit.AppTheme.size.dp1,
                    color = com.example.uikit.AppTheme.colorScheme.primary,
                    shape = com.example.uikit.AppTheme.shape.large
                )
        )
    }
}
