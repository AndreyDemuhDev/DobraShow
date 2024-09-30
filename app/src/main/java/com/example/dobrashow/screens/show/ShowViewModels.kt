package com.example.dobrashow.screens.show
//
//import androidx.lifecycle.ViewModel
//import com.example.view_show.GetAllShowsUseCase
//import dagger.hilt.android.lifecycle.HiltViewModel
//import javax.inject.Inject
//import javax.inject.Provider
//
//@HiltViewModel
//class ShowViewModels @Inject constructor(
//    getAllShowsUseCases: Provider<GetAllShowsUseCase>
//) : ViewModel() {
//
////    private val _listShowState = MutableStateFlow<ShowUiState>(ShowUiState.Loading)
////    val listShowState: StateFlow<ShowUiState> = _listShowState.asStateFlow()
//
////    private val fetchedShowPage = mutableListOf<List<DomainShowEntity>>()
//
////    private val state: StateFlow<State> = getAllShowsUseCases.get().invoke()
////        .map { it.toState() }
////        .stateIn(
////            viewModelScope,
////            SharingStarted.Lazily, State.None
////        )
//
////    fun initialPage() =
////        viewModelScope.launch {
////            if (fetchedShowPage.isNotEmpty()) return@launch
////            val initialPage = showRepository.getListShow(numberPage = 0)
////            initialPage.onSuccessStatus { showPage ->
////                fetchedShowPage.clear()
////                fetchedShowPage.add(showPage)
////                _listShowState.update { return@update ShowUiState.Success(listShow = showPage) }
////            }.onFailure{
////
////            }
////        }
//
////    fun fetchNextPage() =
////        viewModelScope.launch {
////            val nextPageIndex = fetchedShowPage.size + 1
////            showRepository.getListShow(numberPage = nextPageIndex).onSuccess { showPage ->
////                fetchedShowPage.add(showPage)
////                _listShowState.update { currentState ->
////                    val currentShows =
////                        (currentState as? ShowUiState.Success)?.listShow ?: emptyList()
////                    return@update ShowUiState.Success(listShow = currentShows + showPage)
////                }
////            }.onFailure {
////
////            }
////        }
//}
