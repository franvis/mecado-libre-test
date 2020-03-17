package francisco.visintini.mercadolibre.data.search.repository

import francisco.visintini.mercadolibre.data.search.service.SearchService
import francisco.visintini.mercadolibre.data.utils.wrapDataCall
import francisco.visintini.mercadolibre.domain.entity.Result
import francisco.visintini.mercadolibre.domain.entity.Result.Error
import francisco.visintini.mercadolibre.domain.entity.Result.Success
import francisco.visintini.mercadolibre.domain.entity.SearchResult
import francisco.visintini.mercadolibre.domain.repository.ErrorHandler
import francisco.visintini.mercadolibre.domain.repository.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal data class SearchRepositoryImpl @Inject constructor(
    private val searchService: SearchService,
    private val errorHandler: ErrorHandler
) : SearchRepository {
    override suspend fun search(searchQuery: String, page: Int): Result<SearchResult> {
        return wrapDataCall(errorHandler) {
            searchService.search(searchQuery, page).mapToDomain()
        }
    }
}
