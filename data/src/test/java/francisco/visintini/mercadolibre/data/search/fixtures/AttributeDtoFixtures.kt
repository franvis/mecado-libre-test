package francisco.visintini.mercadolibre.data.search.fixtures

import francisco.visintini.mercadolibre.data.search.dto.AttributeDto
import francisco.visintini.mercadolibre.data.search.fixtures.Constants.MOCKED_ATTRIBUTE_ID
import francisco.visintini.mercadolibre.data.search.fixtures.Constants.MOCKED_ATTRIBUTE_NAME
import francisco.visintini.mercadolibre.data.search.fixtures.Constants.MOCKED_ATTRIBUTE_VALUE_NAME

object AttributeDtoFixtures {
    internal fun just() = AttributeDto(
        id = MOCKED_ATTRIBUTE_ID,
        name = MOCKED_ATTRIBUTE_NAME,
        valueName = MOCKED_ATTRIBUTE_VALUE_NAME
    )
}
