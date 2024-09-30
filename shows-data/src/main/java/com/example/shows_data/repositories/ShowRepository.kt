package com.example.shows_data.repositories

//@Suppress("TooManyFunctions")
//class ShowRepository @Inject constructor(
//    private val ktorClient: KtorClient,
//    private val database: ShowsDatabase,
//) {
//
//    suspend fun getListShow(numberPage: Int): Result<List<DomainShowEntity>> {
//        return ktorClient.getListShow(pageNumber = numberPage)
//    }
//
//    // функция которая отображает нам списко сериалов
//    fun getListShow(
//        numberPage: Int,
//        mergeStrategy: MergeStrategy<RequestStatus<List<Shows>>> = DefaultRequestResponseMergeStrategy()
//    ): Flow<RequestStatus<List<Shows>>> {
//        // переменная которая предоставляет сериалы из базы данных
//        val cachedAllShow: Flow<RequestStatus<List<Shows>>> = getAllShowsFromDatabase()
//
//        // переменная которая предоставляет сериалы из сети
//        val remoteShows: Flow<RequestStatus<List<Shows>>> = getAllShowsFromServer(numberPage)
//
//        // возвращаем результаты которые мы получили в результате статуса получения данных из сети и базы данных
//        // результат зависит от работы функции merge интерфейса MergeStrategy
//        return cachedAllShow.combine(remoteShows, mergeStrategy::merge)
//            .flatMapLatest { result ->
//                if (result is RequestStatus.Success) {
//                    database.showsDao.getObservableListShow()
//                        .map { databaseShow -> databaseShow.map { it.toShow() } }
//                        .map { RequestStatus.Success(it) }
//                } else {
//                    flowOf(result)
//                }
//            }
//    }
//
//    // функция которая предоставляет сериалы из сети
//    private fun getAllShowsFromServer(numberPage: Int): Flow<RequestStatus<List<Shows>>> {
//        val apiRequest: Flow<RequestStatus<List<RemoteShowModel>>> =
//            flow { emit(ktorClient.getListShow(pageNumber = numberPage)) }
//                .onEach { result: Result<List<RemoteShowModel>> ->
//                    if (result.isSuccess) {
//                        saveNetworkShowsToDatabase(result.getOrThrow())
//                    }
//                }
//                .map { it.toRequestStatus() }
//
//        val startRequest = flowOf<RequestStatus<List<RemoteShowModel>>>(RequestStatus.InProgress())
//
//        return merge(apiRequest, startRequest).map { result ->
//            result.mapperStatus { showsRemote ->
//                showsRemote.map {
//                    it.toShow()
//                }
//            }
//        }
//    }
//
//    // функция которая сохраняет сериалы из сети в базу данных
//    private suspend fun saveNetworkShowsToDatabase(data: List<RemoteShowModel>) {
//        val networkShows: List<ShowsDBO> =
//            data.map { showsDto: RemoteShowModel -> showsDto.toShowDatabase() }
//        database.showsDao.insertShowToDatabase(networkShows)
//    }
//
//    // функция которая предоставляет сериалы из базы данных
//    private fun getAllShowsFromDatabase(): Flow<RequestStatus<List<Shows>>> {
//        val databaseRequest = database.showsDao::getAllListShow.asFlow()
//            .map<List<ShowsDBO>, RequestStatus<List<ShowsDBO>>> { RequestStatus.Success(it) }
//        val startRequest = flowOf<RequestStatus<List<ShowsDBO>>>(RequestStatus.InProgress())
//
//        return merge(startRequest, databaseRequest)
//            .map { result ->
//                result.mapperStatus { showsDatabase ->
//                    showsDatabase.map { it.toShow() }
//                }
//            }
//    }
//
//    suspend fun getShowInformation(showId: Int): Result<DomainShowEntity> {
//        return ktorClient.getShow(id = showId)
//    }
//
//    suspend fun getListCastShow(showId: Int): Result<List<DomainCastEntity>> {
//        return ktorClient.getCastShow(id = showId)
//    }
//
//    suspend fun getListCrewShow(showId: Int): Result<List<DomainCrewEntity>> {
//        return ktorClient.getCrewShow(id = showId)
//    }
//
//    suspend fun getListSeasonsShow(showId: Int): Result<List<DomainSeasonEntity>> {
//        return ktorClient.getListSeasonsShow(id = showId)
//    }
//
//    suspend fun getSeasonInfo(seasonId: Int): Result<DomainSeasonEntity> {
//        return ktorClient.getSeasonInfo(seasonId = seasonId)
//    }
//
//    suspend fun getInfoPerson(personId: Int): Result<DomainPersonEntity> {
//        return ktorClient.getPersonInfo(personId = personId)
//    }
//
//    suspend fun getListPersons(pageNumber: Int): Result<List<DomainSimplePersonEntity>> {
//        return ktorClient.getListPersons(pageNumber = pageNumber)
//    }
//
//    suspend fun searchShow(query: String): Result<List<DomainSearchShowEntity>> {
//        return ktorClient.searchShow(query = query)
//    }
//}
