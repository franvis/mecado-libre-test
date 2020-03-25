package francisco.visintini.mercadolibre.data.search.fixtures

import francisco.visintini.mercadolibre.data.search.dto.AttributeDto
import francisco.visintini.mercadolibre.data.search.fixtures.Constants.MOCKED_ATTRIBUTE_ID
import francisco.visintini.mercadolibre.data.search.fixtures.Constants.MOCKED_ATTRIBUTE_NAME
import francisco.visintini.mercadolibre.data.search.fixtures.Constants.MOCKED_ATTRIBUTE_VALUE_NAME

internal object AttributeDtoFixtures {
    fun just() = AttributeDto(
        id = MOCKED_ATTRIBUTE_ID,
        name = MOCKED_ATTRIBUTE_NAME,
        valueName = MOCKED_ATTRIBUTE_VALUE_NAME
    )
}
