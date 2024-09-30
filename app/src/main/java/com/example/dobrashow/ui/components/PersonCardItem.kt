package com.example.dobrashow.ui.components
//
//import androidx.compose.foundation.ExperimentalFoundationApi
//import androidx.compose.foundation.basicMarquee
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.interaction.MutableInteractionSource
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.heightIn
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.ElevatedCard
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import coil.compose.AsyncImage
//import com.example.design.theme.AppTheme
//import com.example.dobrashow.R
//import com.example.network.models.domain.DomainSimplePersonEntity
//
//@OptIn(ExperimentalFoundationApi::class)
//@Composable
//fun PersonCardItem(
//    person: DomainSimplePersonEntity,
//    onClickPerson: () -> Unit,
//    modifier: Modifier = Modifier,
//) {
//    ElevatedCard(
//        modifier = modifier.clickable(
//            interactionSource = remember { MutableInteractionSource() },
//            indication = null,
//            onClick = { onClickPerson() }
//        ),
//        elevation = CardDefaults.cardElevation(defaultElevation = AppTheme.size.dp4),
//        shape = RoundedCornerShape(
//            topStart = AppTheme.size.dp16,
//            topEnd = AppTheme.size.dp16,
//            bottomEnd = AppTheme.size.dp8,
//            bottomStart = AppTheme.size.dp8
//        ),
//        colors = CardDefaults.cardColors(containerColor = AppTheme.colorScheme.primary)
//    ) {
//        Column {
//            AsyncImage(
//                model = person.image?.medium,
//                contentDescription = null,
//                error = painterResource(
//                    id = R.drawable.ic_no_photo
//                ),
//                contentScale = ContentScale.Crop,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .heightIn(max = 250.dp)
//                    .padding(all = AppTheme.size.dp4)
//                    .clip(
//                        RoundedCornerShape(
//                            topStart = AppTheme.size.dp16,
//                            topEnd = AppTheme.size.dp16,
//                            bottomEnd = AppTheme.size.dp8,
//                            bottomStart = AppTheme.size.dp8
//                        )
//                    )
//            )
//            Text(
//                text = person.name,
//                style = AppTheme.typography.titleNormal,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .basicMarquee(),
//                textAlign = TextAlign.Center,
//                color = AppTheme.colorScheme.background
//            )
//        }
//    }
//}
