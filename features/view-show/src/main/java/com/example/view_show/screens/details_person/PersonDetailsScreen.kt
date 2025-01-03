package com.example.view_show.screens.details_person

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.design.theme.AppTheme
import com.example.domain.model.PersonShowUi
import com.example.ui.CustomTopBarComponent
import com.example.ui.DescriptionPersonComponent
import com.example.ui.PersonImageComponent
import com.example.ui.dateConverter
import com.example.view_show.R
import com.example.view_show.screens.details_show.LoadingStateContent

@Composable
fun PersonDetailsMainScreen(
    personId: Int,
    onClickBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    PersonDetailScreen(
        idPerson = personId,
        onClickBack = onClickBack,
        modifier = modifier
    )
}

@Composable
internal fun PersonDetailScreen(
    idPerson: Int,
    onClickBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PersonDetailsViewModel = hiltViewModel()
) {
    val statePersonDetail by viewModel.personDetailState.collectAsState()

    LaunchedEffect(key1 = Unit, block = {
        viewModel.getPersonDetails(personId = idPerson)
    })

    if (statePersonDetail != StatePerson.None) {
        DetailStateContent(
            personState = statePersonDetail,
            onClickBack = onClickBack,
            modifier = modifier
        )
    }
}

@Composable
private fun DetailStateContent(
    personState: StatePerson,
    onClickBack: () -> Unit,
    modifier: Modifier,
) {
    if (personState is StatePerson.Error) {
        DetailPersonErrorContent(modifier = modifier)
    }
    if (personState is StatePerson.Loading) {
        LoadingStateContent(
            modifier = modifier
        )
    }
    if (personState.person != null) {
        DetailPersonSuccessContent(person = personState.person, onClickBack, modifier)
    }


}

@Composable
fun DetailPersonErrorContent(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(id = R.string.person_detail_error_status),
        color = AppTheme.colorScheme.primary
    )
}

@Composable
private fun DetailPersonSuccessContent(
    person: PersonShowUi,
    onClickBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column {
        CustomTopBarComponent(
            title = stringResource(id = R.string.detail_person_top_bar),
            onClickBack = onClickBack
        )
        LazyColumn(
            modifier = modifier
        ) {
            item { PersonImageComponent(imageUrl = person.image.medium) }
            item {
                if (person.name.isNotEmpty()) DescriptionPersonComponent(
                    title = stringResource(id = R.string.detail_person_name_text),
                    description = person.name
                )
            }
            item {
                if (person.birthday.isNotEmpty()) DescriptionPersonComponent(
                    title = stringResource(id = R.string.detail_person_birthday_text),
                    description = dateConverter(person.birthday)
                )
            }
            item {
                if (person.country.name.isNotEmpty()) DescriptionPersonComponent(
                    title = stringResource(id = R.string.detail_person_country_text),
                    description = person.country.name
                )
            }
        }
    }
}
