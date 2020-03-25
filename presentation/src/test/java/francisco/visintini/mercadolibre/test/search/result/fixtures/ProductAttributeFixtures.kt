package francisco.visintini.mercadolibre.test.search.result.fixtures

import francisco.visintini.mercadolibre.domain.entity.ProductAttribute
import francisco.visintini.mercadolibre.test.search.result.fixtures.Constants.MOCKED_ATTRIBUTE_ID
import francisco.visintini.mercadolibre.test.search.result.fixtures.Constants.MOCKED_ATTRIBUTE_NAME
import francisco.visintini.mercadolibre.test.search.result.fixtures.Constants.MOCKED_ATTRIBUTE_VALUE_NAME

object ProductAttributeFixtures {
    fun just() = ProductAttribute(
        MOCKED_ATTRIBUTE_ID,
        MOCKED_ATTRIBUTE_NAME,
        MOCKED_ATTRIBUTE_VALUE_NAME
    )
}
