package francisco.visintini.mercadolibre.data.search.fixtures

import francisco.visintini.mercadolibre.data.search.dto.ShippingDto
import francisco.visintini.mercadolibre.data.search.fixtures.Constants.MOCKED_IS_FREE_SHIPPING

object ShippingDtoFixtures {
    internal fun just() = ShippingDto(freeShipping = MOCKED_IS_FREE_SHIPPING)
}
