package com.example.dobrashow.screens

import android.text.Html
import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.dobrashow.R
import com.example.network.KtorClient
import com.example.network.models.domain.DomainShowEntity
import kotlinx.coroutines.delay

@Composable
fun DetailShowScreen(
    ktorClient: KtorClient,
    showId: Int,
    modifier: Modifier = Modifier
) {


    var show by remember {
        mutableStateOf<DomainShowEntity?>(null)
    }

    LaunchedEffect(key1 = Unit, block = {
        delay(500)
        show = ktorClient.getShow(showId)
    })


    LazyColumn() {
        if (show == null) {
            item { CircularProgressIndicator() }
            return@LazyColumn
        }

        item { ImageShowSection(show) }
        item { Spacer(modifier = Modifier.height(10.dp)) }
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 38.dp)
            ) {
                InformationShowItem(
                    icon = R.drawable.ic_calendar,
                    title = show?.premiered?.substring(0, endIndex = 4) ?: "unknown"
                )
                InformationShowItem(
                    icon = R.drawable.ic_film,
                    title = show?.genres?.first() ?: "unknown"
                )
                InformationShowItem(
                    icon = R.drawable.ic_rating_star,
                    title = show?.rating?.average.toString()
                )
            }
        }
        item { Spacer(modifier = Modifier.height(10.dp)) }
        item {
            Text(
                text = Html.fromHtml(show?.summary).toString(),
                textAlign = TextAlign.Justify,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ImageShowSection(show: DomainShowEntity?) {
    Box(modifier = Modifier) {
        AsyncImage(
            model = show?.image?.medium, contentDescription = null,
            modifier = Modifier
                .aspectRatio(1f)
                .heightIn(500.dp),
            contentScale = ContentScale.FillBounds,
        )
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, top = 45.dp, bottom = 20.dp),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = "back",
                    modifier = Modifier.size(36.dp)
                )
                Text(
                    text = show?.name ?: "unknown show name",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.basicMarquee()
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_favorite_heart),
                    contentDescription = "favorite",
                    modifier = Modifier.size(36.dp)
                )
            }
            AsyncImage(
                model = show?.image?.medium, contentDescription = null,
                modifier = Modifier
                    .size(width = 220.dp, height = 300.dp)
                    .border(2.dp, color = Color.DarkGray),

                contentScale = ContentScale.Crop,
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            0.0f to Color.Transparent,
                            100.0f to Color.Black,
                            startY = 0.0f,
                            endY = 100f
                        )
                    )
                    .padding(top = 40.dp)
            ) { }
        }
    }
}

@Composable
fun InformationShowItem(
    @DrawableRes icon: Int,
    title: String,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = title,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 2.dp)
        )
    }
}