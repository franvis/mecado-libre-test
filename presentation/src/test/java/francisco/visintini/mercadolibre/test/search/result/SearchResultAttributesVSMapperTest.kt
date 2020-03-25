package francisco.visintini.mercadolibre.test.search.result

import francisco.visintini.mercadolibre.test.search.result.fixtures.Constants
import francisco.visintini.mercadolibre.test.search.result.fixtures.ProductAttributeFixtures
import francisco.visintini.mercadolibre.test.search.result.fixtures.SearchResultAttributesItemVSFixtures
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test

@Suppress("MaxLineLength")
class SearchResultAttributesVSMapperTest {

    private lateinit var searchResultAttributesVSMapper: SearchResultAttributesVSMapper

    @Before
    fun setup() {
        searchResultAttributesVSMapper = SearchResultAttributesVSMapper()
    }

    @Test
    fun `mapToViewState - with 3 complete attributes - returns correct view state`() {
        // GIVEN
        val productAttribute = ProductAttributeFixtures.just()
        val attributes = listOf(
            productAttribute,
            productAttribute.copy(valueName = Constants.MOCKED_ATTRIBUTE_VALUE_NAME_2),
            productAttribute.copy(valueName = Constants.MOCKED_ATTRIBUTE_VALUE_NAME_3)
        )

        // WHEN
        val viewState = searchResultAttributesVSMapper.mapToViewState(attributes)

        // THEN
        viewState shouldEqual SearchResultAttributes.ViewState(
            listOf(
                SearchResultAttributesItemVSFixtures.just(),
                SearchResultAttributesItemVSFixtures.just(Constants.MOCKED_ATTRIBUTE_VALUE_NAME_2),
                SearchResultAttributesItemVSFixtures.just(Constants.MOCKED_ATTRIBUTE_VALUE_NAME_3)
            )
        )
    }

    @Test
    fun `mapToViewState - with 2 complete attributes and one with null value name - returns correct view state filtering null value names`() {
        // GIVEN
        val productAttribute = ProductAttributeFixtures.just()
        val attributes = listOf(
            productAttribute,
            productAttribute.copy(valueName = null),
            productAttribute.copy(valueName = Constants.MOCKED_ATTRIBUTE_VALUE_NAME_3)
        )

        // WHEN
        val viewState = searchResultAttributesVSMapper.mapToViewState(attributes)

        // THEN
        viewState shouldEqual SearchResultAttributes.ViewState(
            listOf(
                SearchResultAttributesItemVSFixtures.just(),
                SearchResultAttributesItemVSFixtures.just(Constants.MOCKED_ATTRIBUTE_VALUE_NAME_3)
            )
        )
    }
}
