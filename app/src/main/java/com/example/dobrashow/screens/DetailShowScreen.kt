package com.example.dobrashow.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import coil.compose.SubcomposeAsyncImage
import com.example.dobrashow.R
import com.example.dobrashow.ui.components.CastAndCrewShowPager
import com.example.dobrashow.ui.components.ShowStatusComponent
import com.example.network.KtorClient
import com.example.network.models.domain.DomainCastEntity
import com.example.network.models.domain.DomainShowEntity

@Composable
fun DetailShowScreen(
    ktorClient: KtorClient,
    showId: Int,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {

    var show by remember {
        mutableStateOf<DomainShowEntity?>(null)
    }
    var cast by remember {
        mutableStateOf<List<DomainCastEntity>>(listOf())
    }

    LaunchedEffect(key1 = Unit, block = {
        ktorClient.getShow(showId)
            .onSuccess { getApiShow ->
                show = getApiShow
            }.onException { exception ->
                // todo
            }
        ktorClient.getCastShow(showId)
            .onSuccess { getCast ->
                cast = getCast
            }.onException { exception ->
                // todo
            }
    })

    LazyColumn(modifier = modifier) {
        if (show == null) {
            item { LoadingState(modifier = Modifier.fillMaxSize()) }
            return@LazyColumn
        }
        item { ImageShowSection(show!!) }
        item {
            Text(
                text = "Story Line",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp, top = 10.dp)
            )
        }
        item {
            Text(
                text = HtmlCompat.fromHtml(show!!.summary, HtmlCompat.FROM_HTML_MODE_COMPACT)
                    .toString().trim(),
                textAlign = TextAlign.Justify,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
        item {
            CastAndCrewShowPager(
                cast = cast,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )
        }
        item {
            Text(
                text = "Seasons",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(start = 16.dp, top = 10.dp, bottom = 10.dp)
            )
        }
    }

}


@Composable
private fun ImageShowSection(show: DomainShowEntity, modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        SubcomposeAsyncImage(
            model = show.image.medium, contentDescription = null,
            modifier = Modifier
                .aspectRatio(1f)
                .heightIn(max = 400.dp),
            contentScale = ContentScale.FillBounds,
            loading = { LoadingState() }
        )
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .heightIn(min = 400.dp)
                .padding(horizontal = 16.dp)
        ) {
            HeaderInformationShowSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 45.dp)
            )
            InformationShowItem(show = show, modifier = Modifier.align(Alignment.Start))
        }
    }
}

@Composable
private fun HeaderInformationShowSection(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_arrow_back),
            contentDescription = "back",
            colorFilter = ColorFilter.tint(Color.White),
            modifier = Modifier.size(36.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.ic_favorite_heart),
            contentDescription = "favorite",
            colorFilter = ColorFilter.tint(Color.White),
            modifier = Modifier.size(36.dp)
        )
    }
}

@Composable
fun InformationShowItem(
    show: DomainShowEntity,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
    ) {
        Text(
            text = show.name,
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White
        )
        Row {
            Text(
                text = show.genres.joinToString(separator = ", "),
                style = MaterialTheme.typography.titleSmall,
                color = Color.White
            )
            Text(
                text = " • " + show.premiered.substring(startIndex = 0, endIndex = 4),
                style = MaterialTheme.typography.titleSmall,
                color = Color.White
            )
            Text(
                text = " • " + show.network.country.code,
                style = MaterialTheme.typography.titleSmall,
                color = Color.White
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.ic_rating_star),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.Yellow),
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = show.rating.average.toString(),
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                modifier = Modifier.padding(start = 2.dp)
            )
            ShowStatusComponent(showStatus = show.status, modifier = Modifier.padding(start = 8.dp))
        }
    }
}

@Composable
fun LoadingState(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            modifier = Modifier
                .fillMaxSize()
                .padding(100.dp),
        )
    }
}