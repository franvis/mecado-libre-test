package francisco.visintini.mercadolibre.test.search.result.fixtures

import francisco.visintini.mercadolibre.domain.entity.ShippingInfo
import francisco.visintini.mercadolibre.domain.entity.SummarizedProduct
import francisco.visintini.mercadolibre.test.search.result.fixtures.Constants.MOCKED_IS_FREE_SHIPPING
import francisco.visintini.mercadolibre.test.search.result.fixtures.Constants.MOCKED_PRODUCT_AVAILABLE_QUANTITY
import francisco.visintini.mercadolibre.test.search.result.fixtures.Constants.MOCKED_PRODUCT_ID
import francisco.visintini.mercadolibre.test.search.result.fixtures.Constants.MOCKED_PRODUCT_PRICE
import francisco.visintini.mercadolibre.test.search.result.fixtures.Constants.MOCKED_PRODUCT_SITE_ID
import francisco.visintini.mercadolibre.test.search.result.fixtures.Constants.MOCKED_PRODUCT_SOLD_QUANTITY
import francisco.visintini.mercadolibre.test.search.result.fixtures.Constants.MOCKED_PRODUCT_THUMBNAIL_URL
import francisco.visintini.mercadolibre.test.search.result.fixtures.Constants.MOCKED_PRODUCT_TITLE

object SummarizedProductFixtures {
    fun just() = SummarizedProduct(
        MOCKED_PRODUCT_ID,
        MOCKED_PRODUCT_SITE_ID,
        MOCKED_PRODUCT_TITLE,
        MOCKED_PRODUCT_PRICE,
        MOCKED_PRODUCT_AVAILABLE_QUANTITY,
        MOCKED_PRODUCT_SOLD_QUANTITY,
        MOCKED_PRODUCT_THUMBNAIL_URL,
        ShippingInfo(MOCKED_IS_FREE_SHIPPING),
        listOf(ProductAttributeFixtures.just())
    )
}
