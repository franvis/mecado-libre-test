package francisco.visintini.mercadolibre.data.search.fixtures

import francisco.visintini.mercadolibre.data.search.fixtures.Constants.MOCKED_ATTRIBUTE_ID
import francisco.visintini.mercadolibre.data.search.fixtures.Constants.MOCKED_ATTRIBUTE_NAME
import francisco.visintini.mercadolibre.data.search.fixtures.Constants.MOCKED_ATTRIBUTE_VALUE_NAME
import francisco.visintini.mercadolibre.data.search.fixtures.Constants.MOCKED_IS_FREE_SHIPPING
import francisco.visintini.mercadolibre.data.search.fixtures.Constants.MOCKED_PAGING_LIMIT
import francisco.visintini.mercadolibre.data.search.fixtures.Constants.MOCKED_PAGING_OFFSET
import francisco.visintini.mercadolibre.data.search.fixtures.Constants.MOCKED_PAGING_PRIMARY_RESULTS
import francisco.visintini.mercadolibre.data.search.fixtures.Constants.MOCKED_PAGING_TOTAL
import francisco.visintini.mercadolibre.data.search.fixtures.Constants.MOCKED_PRODUCT_AVAILABLE_QUANTITY
import francisco.visintini.mercadolibre.data.search.fixtures.Constants.MOCKED_PRODUCT_ID
import francisco.visintini.mercadolibre.data.search.fixtures.Constants.MOCKED_PRODUCT_PRICE
import francisco.visintini.mercadolibre.data.search.fixtures.Constants.MOCKED_PRODUCT_SITE_ID
import francisco.visintini.mercadolibre.data.search.fixtures.Constants.MOCKED_PRODUCT_SOLD_QUANTITY
import francisco.visintini.mercadolibre.data.search.fixtures.Constants.MOCKED_PRODUCT_THUMBNAIL_URL
import francisco.visintini.mercadolibre.data.search.fixtures.Constants.MOCKED_PRODUCT_TITLE
import francisco.visintini.mercadolibre.data.search.fixtures.Constants.MOCKED_SEARCH_QUERY
import francisco.visintini.mercadolibre.data.search.fixtures.Constants.MOCKED_SEARCH_SITE_ID
import francisco.visintini.mercadolibre.domain.entity.PagingInfo
import francisco.visintini.mercadolibre.domain.entity.ProductAttribute
import francisco.visintini.mercadolibre.domain.entity.SearchResult
import francisco.visintini.mercadolibre.domain.entity.ShippingInfo
import francisco.visintini.mercadolibre.domain.entity.SummarizedProduct

object SearchResultFixtures {
    internal fun just() = SearchResult(
        siteId = MOCKED_SEARCH_SITE_ID,
        query = MOCKED_SEARCH_QUERY,
        paging = PagingInfo(
            MOCKED_PAGING_TOTAL,
            MOCKED_PAGING_OFFSET,
            MOCKED_PAGING_LIMIT,
            MOCKED_PAGING_PRIMARY_RESULTS
        ),
        results = listOf(
            SummarizedProduct(
                MOCKED_PRODUCT_ID,
                MOCKED_PRODUCT_SITE_ID,
                MOCKED_PRODUCT_TITLE,
                MOCKED_PRODUCT_PRICE,
                MOCKED_PRODUCT_AVAILABLE_QUANTITY,
                MOCKED_PRODUCT_SOLD_QUANTITY,
                MOCKED_PRODUCT_THUMBNAIL_URL,
                ShippingInfo(MOCKED_IS_FREE_SHIPPING),
                listOf(
                    ProductAttribute(
                        MOCKED_ATTRIBUTE_ID,
                        MOCKED_ATTRIBUTE_NAME,
                        MOCKED_ATTRIBUTE_VALUE_NAME
                    )
                )
            )
        )
    )
}
