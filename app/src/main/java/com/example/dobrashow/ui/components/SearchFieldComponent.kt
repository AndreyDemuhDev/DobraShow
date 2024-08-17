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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.MaterialTheme
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
import com.example.dobrashow.ui.theme.AppTheme

@Composable
fun SearchFieldComponent(
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier,
    query: String = "",
) {

    var queryShow by remember { mutableStateOf(query) }

    Row(
        modifier = modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth()
            .background(
                color = AppTheme.colorScheme.background,
                shape = AppTheme.shape.large
            )
            .border(
                width = AppTheme.size.dp1,
                color = AppTheme.colorScheme.primary,
                shape = AppTheme.shape.large
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = queryShow, onValueChange = { queryShow = it },
            placeholder = { Text(text = "Search", style = AppTheme.typography.bodyNormal) },
            singleLine = true,
            maxLines = 1,
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = AppTheme.colorScheme.background,
                focusedIndicatorColor = AppTheme.colorScheme.background
            ),
            shape = AppTheme.shape.large,
            trailingIcon = {
//                if (queryShow.isEmpty()) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "search",
                        modifier = Modifier
                            .padding(end = AppTheme.size.dp8)
                            .size(38.dp)
                            .background(
                                color = AppTheme.colorScheme.onText,
                                shape = AppTheme.shape.large
                            )
                            .padding(all = AppTheme.size.dp8)
                            .clickable { onSearch(queryShow) },
                        colorFilter = ColorFilter.tint(AppTheme.colorScheme.primary)
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
                    color = AppTheme.colorScheme.background,
                    shape = AppTheme.shape.small
                )
                .width(40.dp)
                .border(
                    width = AppTheme.size.dp1,
                    color = AppTheme.colorScheme.primary,
                    shape = AppTheme.shape.large
                )
        )
    }
}