package francisco.visintini.mercadolibre.data.search.fixtures

import francisco.visintini.mercadolibre.data.search.dto.SummarizedProductDto
import francisco.visintini.mercadolibre.data.search.fixtures.Constants.MOCKED_PRODUCT_AVAILABLE_QUANTITY
import francisco.visintini.mercadolibre.data.search.fixtures.Constants.MOCKED_PRODUCT_ID
import francisco.visintini.mercadolibre.data.search.fixtures.Constants.MOCKED_PRODUCT_PRICE
import francisco.visintini.mercadolibre.data.search.fixtures.Constants.MOCKED_PRODUCT_SITE_ID
import francisco.visintini.mercadolibre.data.search.fixtures.Constants.MOCKED_PRODUCT_SOLD_QUANTITY
import francisco.visintini.mercadolibre.data.search.fixtures.Constants.MOCKED_PRODUCT_THUMBNAIL_URL
import francisco.visintini.mercadolibre.data.search.fixtures.Constants.MOCKED_PRODUCT_TITLE

internal object SummarizedProductDtoFixtures {
    fun just() = SummarizedProductDto(
        id = MOCKED_PRODUCT_ID,
        siteId = MOCKED_PRODUCT_SITE_ID,
        title = MOCKED_PRODUCT_TITLE,
        price = MOCKED_PRODUCT_PRICE,
        availableQuantity = MOCKED_PRODUCT_AVAILABLE_QUANTITY,
        soldQuantity = MOCKED_PRODUCT_SOLD_QUANTITY,
        thumbnailUrl = MOCKED_PRODUCT_THUMBNAIL_URL,
        shipping = ShippingDtoFixtures.just(),
        attributes = listOf(AttributeDtoFixtures.just())
    )
}
