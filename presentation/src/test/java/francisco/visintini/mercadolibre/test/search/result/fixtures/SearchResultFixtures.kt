package francisco.visintini.mercadolibre.test.search.result.fixtures

import francisco.visintini.mercadolibre.domain.entity.PagingInfo
import francisco.visintini.mercadolibre.domain.entity.SearchResult

object SearchResultFixtures {
    fun just() = SearchResult(
        siteId = Constants.MOCKED_SEARCH_SITE_ID,
        query = Constants.MOCKED_SEARCH_QUERY,
        paging = PagingInfo(1, 1, 1, 1),
        results = listOf(SummarizedProductFixtures.just())
    )
}
