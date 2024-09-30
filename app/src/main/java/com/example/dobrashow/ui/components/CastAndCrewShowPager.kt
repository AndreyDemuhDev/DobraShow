package com.example.dobrashow.ui.components
//
//import androidx.compose.foundation.ExperimentalFoundationApi
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.lazy.LazyRow
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.pager.HorizontalPager
//import androidx.compose.foundation.pager.rememberPagerState
//import androidx.compose.material3.Tab
//import androidx.compose.material3.TabRow
//import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
//import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableIntStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.text.font.FontWeight
//import com.example.design.theme.AppTheme
//import com.example.network.models.domain.DomainCastEntity
//import com.example.network.models.domain.DomainCrewEntity
//
//@OptIn(ExperimentalFoundationApi::class)
//@Composable
//@Suppress("LongMethod")
//fun CastAndCrewShowPager(
//    cast: List<DomainCastEntity>,
//    crew: List<DomainCrewEntity>,
//    onClickPerson: (Int) -> Unit,
//    modifier: Modifier = Modifier
//) {
//    val tabItems = listOf("Cast", "Crew")
//    var selectedTabIndex by remember { mutableIntStateOf(0) }
//    val pagerState = rememberPagerState { tabItems.size }
//
//    LaunchedEffect(key1 = selectedTabIndex) {
//        pagerState.animateScrollToPage(selectedTabIndex)
//    }
//    LaunchedEffect(key1 = pagerState.currentPage, key2 = pagerState.isScrollInProgress) {
//        if (!pagerState.isScrollInProgress) {
//            selectedTabIndex = pagerState.currentPage
//        }
//    }
//    TabRow(
//        selectedTabIndex = selectedTabIndex,
//        containerColor = AppTheme.colorScheme.background,
//        indicator = { tabPositions ->
//            SecondaryIndicator(
//                modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
//                color = AppTheme.colorScheme.primary
//            )
//        },
//        modifier = modifier
//    ) {
//        tabItems.forEachIndexed { index, _ ->
//            Tab(
//                selected = selectedTabIndex == index,
//                onClick = { selectedTabIndex = index },
//                text = {
//                    Text(
//                        text = tabItems[index],
//                        style = AppTheme.typography.bodyLarge,
//                        fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal,
//                        color = if (selectedTabIndex == index) {
//                            AppTheme.colorScheme.primary
//                        } else AppTheme.colorScheme.text
//                    )
//                }
//            )
//        }
//    }
//    HorizontalPager(
//        state = pagerState,
//        modifier = Modifier
//            .fillMaxWidth()
//    ) { index ->
//        Box(modifier = Modifier.fillMaxWidth()) {
//            if (index == 0) {
//                LazyRow(modifier = Modifier.padding(horizontal = AppTheme.size.dp12)) {
//                    items(cast) { item ->
//                        CastItemCard(
//                            cast = item,
//                            onClickPerson = { onClickPerson(item.person.id) },
//                            modifier = Modifier.padding(horizontal = AppTheme.size.dp4)
//                        )
//                    }
//                }
//            } else {
//                LazyRow(modifier = Modifier.padding(horizontal = AppTheme.size.dp12)) {
//                    items(crew) { item ->
//                        CrewItemCard(
//                            crew = item,
//                            onClickPerson = { onClickPerson(item.person.id) },
//                            modifier = Modifier.padding(horizontal = AppTheme.size.dp4)
//                        )
//                    }
//                }
//            }
//        }
//    }
//}
