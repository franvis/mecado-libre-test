package francisco.visintini.mercadolibre.domain.repository

import francisco.visintini.mercadolibre.domain.entity.Result
import francisco.visintini.mercadolibre.domain.entity.SearchResult

interface SearchRepository {
    suspend fun search(searchQuery: String, page: Int): Result<SearchResult>
}
