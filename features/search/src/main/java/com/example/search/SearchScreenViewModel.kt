package com.example.search

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.ShowsUi
import com.example.domain.usecases.GetSearchShowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@OptIn(ExperimentalFoundationApi::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchShowUseCase: Provider<GetSearchShowUseCase>
) : ViewModel() {

    val searchTextFieldState = TextFieldState()

    sealed interface SearchState {
        object Empty : SearchState
        data class SearchQuery(val query: String) : SearchState
    }

    private val _uiState = MutableStateFlow<SearchShowScreenState>(SearchShowScreenState.None)
    val uiState = _uiState.asStateFlow()

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private val searchTextState: StateFlow<SearchState> = snapshotFlow { searchTextFieldState.text }
        .debounce(500)
        .mapLatest { if (it.isBlank()) SearchState.Empty else SearchState.SearchQuery(it.toString()) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 2000),
            initialValue = SearchState.Empty
        )


    fun observeSearchShow() = viewModelScope.launch {
        searchTextState.collectLatest { state ->
            when (state) {
                is SearchState.Empty -> _uiState.update { SearchShowScreenState.None }
                is SearchState.SearchQuery -> {
                    Log.d("MyLog", "observeSearchShow = ${state}")
                    searchShow(query = state.query)
                }
            }
        }

    }

    private fun searchShow(query: String) = viewModelScope.launch {
        _uiState.update { SearchShowScreenState.Loading }
        searchShowUseCase.get().searchShow(query = query).collect { show ->
            _uiState.update {
                SearchShowScreenState.Success(
                    listShows = show.data ?: emptyList()
                )
            }
        }
    }

    sealed interface SearchShowScreenState {
        object None : SearchShowScreenState
        object Loading : SearchShowScreenState
        data class Error(val error: String) : SearchShowScreenState
        data class Success(val listShows: List<ShowsUi>) :
            SearchShowScreenState
    }
}
