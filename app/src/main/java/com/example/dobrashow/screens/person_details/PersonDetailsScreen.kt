package com.example.dobrashow.screens.person_details

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.dobrashow.R
import com.example.dobrashow.ui.components.CustomTopBarComponent
import com.example.dobrashow.ui.theme.AppTheme
import com.example.dobrashow.utils.dateConverter
import com.example.network.models.domain.DomainPersonEntity

@Composable
fun PersonDetailsScreen(
    personId: Int,
    onClickBack: () -> Unit,
    onClickShow: (Int) -> Unit,
    onClickCrew: (Int) -> Unit,
    modifier: Modifier = Modifier,
    personViewModel: PersonViewModel = hiltViewModel()
) {

    val state by personViewModel.personDetailUiState.collectAsState()

    LaunchedEffect(key1 = Unit, block = { personViewModel.getPeopleInfo(personId = personId) })

    when (val stateUi = state) {
        is PersonDetailUiState.Error -> {
            Text(text = "Error person screen")
        }

        PersonDetailUiState.Loading -> {
            Text(text = "Loading person screen")
        }

        is PersonDetailUiState.Success -> {
            SuccessPersonStateContent(
                person = stateUi.person,
                onClickShow = onClickShow,
                onClickCrew = onClickCrew,
                onClickBack = onClickBack,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

        }
    }
}

@Composable
fun SuccessPersonStateContent(
    person: DomainPersonEntity,
    onClickShow: (Int) -> Unit,
    onClickCrew: (Int) -> Unit,
    onClickBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        CustomTopBarComponent(title = "Detail Person", onClickBack = onClickBack)
        LazyColumn(
            modifier = modifier
        ) {
            item { PersonImage(imageUrl = person.image.medium) }
            item {
                if (person.name.isNotEmpty()) DescriptionPerson(
                    title = "Name",
                    description = person.name
                )
            }
            item {
                if (person.birthday.isNotEmpty()) DescriptionPerson(
                    title = "Birthday",
                    description = dateConverter(person.birthday)
                )
            }
            item {
                if (person.country.name.isNotEmpty()) DescriptionPerson(
                    title = "Country",
                    description = person.country.name
                )
            }
            item {
                if (person.embedded.castcredits.isNotEmpty()) CastShow(
                    person = person,
                    onClickShow = onClickShow
                )
            }
            item {
                if (person.embedded.crewcredits.isNotEmpty()) CrewShow(
                    person = person,
                    onClickCrew = onClickCrew
                )
            }
        }
    }
}


@Composable
fun PersonImage(imageUrl: String, modifier: Modifier = Modifier) {
    AsyncImage(
        model = imageUrl,
        contentDescription = "Character image",
        error = painterResource(id = R.drawable.ic_no_photo),
        contentScale = ContentScale.Crop,
        modifier = modifier
            .aspectRatio(1f)
            .clip(AppTheme.shape.small)
            .border(
                width = AppTheme.size.dp1,
                brush = Brush.verticalGradient(
                    listOf(
                        AppTheme.colorScheme.transparent,
                        AppTheme.colorScheme.primary
                    )
                ),
                shape = MaterialTheme.shapes.small
            ),
    )
}

@Composable
fun DescriptionPerson(
    title: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = modifier.padding(vertical = AppTheme.size.dp2)
    ) {
        Text(
            text = title,
            style = AppTheme.typography.titleSmall,
            color = AppTheme.colorScheme.text
        )
        Text(
            text = description,
            style = AppTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = AppTheme.colorScheme.text
        )
    }
}

@Composable
fun CastShow(
    person: DomainPersonEntity,
    onClickShow: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(AppTheme.shape.small)
            .background(AppTheme.colorScheme.primary.copy(alpha = 0.1f))
            .border(
                width = AppTheme.size.dp1,
                color = AppTheme.colorScheme.primary.copy(alpha = 0.2f),
                shape = AppTheme.shape.small
            )
            .padding(start = AppTheme.size.dp4)
    ) {
        Text(
            text = "Cast",
            style = AppTheme.typography.titleLarge,
            color = AppTheme.colorScheme.text
        )
        person.embedded.castcredits.forEach { personShow ->
            val linkShow = personShow.linksCast.show.href.substringAfterLast("/")
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(vertical = AppTheme.size.dp4)
                    .clickable { onClickShow(linkShow.toInt()) }
            ) {
                Text(
                    text = personShow.linksCast.show.name,
                    style = AppTheme.typography.bodyLarge,
                    color = AppTheme.colorScheme.text,
                )
                Spacer(modifier = Modifier.width(AppTheme.size.dp10))
                Text(
                    text = "(${personShow.linksCast.character.name})",
                    style = AppTheme.typography.bodyNormal,
                    color = AppTheme.colorScheme.text
                )
            }
            HorizontalDivider(
                thickness = AppTheme.size.dp1,
                color = AppTheme.colorScheme.primary.copy(alpha = 0.5f)
            )
        }
    }
}

@Composable
fun CrewShow(
    person: DomainPersonEntity,
    onClickCrew: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(AppTheme.shape.small)
            .background(AppTheme.colorScheme.primary.copy(alpha = 0.1f))
            .border(
                width = AppTheme.size.dp1,
                color = AppTheme.colorScheme.primary.copy(alpha = 0.2f),
                shape = AppTheme.shape.small
            )
            .padding(start = AppTheme.size.dp4)
    ) {
        Text(
            text = "Crew",
            style = AppTheme.typography.titleLarge,
            color = AppTheme.colorScheme.text
        )
        person.embedded.crewcredits.forEach { personCrew ->
            val linkCrew = personCrew.links.show.href.substringAfterLast("/")
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(vertical = AppTheme.size.dp4)
                    .clickable { onClickCrew(linkCrew.toInt()) }
            ) {
                Text(
                    text = personCrew.links.show.name,
                    style = AppTheme.typography.bodyLarge,
                    color = AppTheme.colorScheme.text,
                )
                Spacer(modifier = Modifier.width(AppTheme.size.dp10))
                Text(
                    text = "(${personCrew.type})",
                    style = AppTheme.typography.bodyNormal,
                    color = AppTheme.colorScheme.text
                )
            }
            HorizontalDivider(
                thickness = AppTheme.size.dp1,
                color = AppTheme.colorScheme.onPrimary.copy(alpha = 0.5f)
            )
        }
    }
}
