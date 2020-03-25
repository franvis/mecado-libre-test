package francisco.visintini.mercadolibre.test.search.result.fixtures

import francisco.visintini.mercadolibre.test.search.result.SearchResultAttributeItem

internal object SearchResultAttributesItemVSFixtures {

    fun just(valueName: String = Constants.MOCKED_ATTRIBUTE_VALUE_NAME) =
        SearchResultAttributeItem.ViewState(
            name = Constants.MOCKED_ATTRIBUTE_NAME,
            description = valueName
        )
}
