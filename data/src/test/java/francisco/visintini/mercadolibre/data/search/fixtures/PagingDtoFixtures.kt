package francisco.visintini.mercadolibre.data.search.fixtures

import francisco.visintini.mercadolibre.data.search.dto.PagingDto
import francisco.visintini.mercadolibre.data.search.fixtures.Constants.MOCKED_PAGING_LIMIT
import francisco.visintini.mercadolibre.data.search.fixtures.Constants.MOCKED_PAGING_OFFSET
import francisco.visintini.mercadolibre.data.search.fixtures.Constants.MOCKED_PAGING_PRIMARY_RESULTS
import francisco.visintini.mercadolibre.data.search.fixtures.Constants.MOCKED_PAGING_TOTAL

object PagingDtoFixtures {
    internal fun just() = PagingDto(
        total = MOCKED_PAGING_TOTAL,
        offset = MOCKED_PAGING_OFFSET,
        limit = MOCKED_PAGING_LIMIT,
        primaryResults = MOCKED_PAGING_PRIMARY_RESULTS
    )
}
