package francisco.visintini.mercadolibre.domain.interactor

import francisco.visintini.mercadolibre.domain.entity.Result
import francisco.visintini.mercadolibre.domain.entity.SearchResult
import francisco.visintini.mercadolibre.domain.repository.SearchRepository
import javax.inject.Inject

class GetSearchResult @Inject constructor(private val searchRepository: SearchRepository) {

    suspend fun execute(query: String, page: Int = 0): Result<SearchResult> {
        return searchRepository.search(query, page)
    }
}
