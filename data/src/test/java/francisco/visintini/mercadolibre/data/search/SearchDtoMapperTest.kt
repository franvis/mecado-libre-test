package francisco.visintini.mercadolibre.data.search

import francisco.visintini.mercadolibre.data.search.fixtures.Constants.MOCKED_PRODUCT_ID_2
import francisco.visintini.mercadolibre.data.search.fixtures.Constants.MOCKED_PRODUCT_ID_3
import francisco.visintini.mercadolibre.data.search.fixtures.SearchDtoFixtures
import francisco.visintini.mercadolibre.data.search.fixtures.SearchResultFixtures
import francisco.visintini.mercadolibre.data.search.fixtures.SummarizedProductDtoFixtures
import org.amshove.kluent.shouldEqual
import org.junit.Test

class SearchDtoMapperTest {

    @Test
    fun `search dto mapper - with full search dto - maps correctly to domain`() {
        SearchDtoFixtures.just().mapToDomain() shouldEqual SearchResultFixtures.just()
    }

    @Test
    fun `search dto mapper - with some null results - maps correctly to domain avoiding nulls`() {
        val domainResult = SearchDtoFixtures.just().copy(
            results = listOf(
                SummarizedProductDtoFixtures.just().copy(id = MOCKED_PRODUCT_ID_2),
                SummarizedProductDtoFixtures.just().copy(price = null),
                SummarizedProductDtoFixtures.just().copy(id = MOCKED_PRODUCT_ID_3)
            )
        ).mapToDomain()
        domainResult.results.size shouldEqual 2
        domainResult.results[0].id shouldEqual MOCKED_PRODUCT_ID_2
        domainResult.results[1].id shouldEqual MOCKED_PRODUCT_ID_3
    }
}
