package francisco.visintini.mercadolibre.data.search.fixtures

import francisco.visintini.mercadolibre.data.search.dto.SearchDto
import francisco.visintini.mercadolibre.data.search.fixtures.Constants.MOCKED_SEARCH_QUERY
import francisco.visintini.mercadolibre.data.search.fixtures.Constants.MOCKED_SEARCH_SITE_ID

object SearchDtoFixtures {
    internal fun just() = SearchDto(
        siteId = MOCKED_SEARCH_SITE_ID,
        query = MOCKED_SEARCH_QUERY,
        paging = PagingDtoFixtures.just(),
        results = listOf(SummarizedProductDtoFixtures.just())
    )
}
