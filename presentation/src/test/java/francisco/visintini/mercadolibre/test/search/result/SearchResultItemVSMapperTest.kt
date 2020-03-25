package francisco.visintini.mercadolibre.test.search.result

import francisco.visintini.mercadolibre.test.R
import francisco.visintini.mercadolibre.test.search.result.fixtures.SummarizedProductFixtures
import francisco.visintini.mercadolibre.test.utils.ResourceProvider
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.math.roundToInt
import org.junit.Before
import org.junit.Test

@Suppress("MaxLineLength")
class SearchResultItemVSMapperTest {

    private lateinit var searchResultItemVSMapper: SearchResultItemVSMapper
    private lateinit var searchResultAttributesVSMapper: SearchResultAttributesVSMapper
    private lateinit var resourceProvider: ResourceProvider

    @Before
    fun setup() {
        searchResultAttributesVSMapper = mockk(relaxed = true)
        resourceProvider = mockk(relaxed = true)
        searchResultItemVSMapper =
            SearchResultItemVSMapper(resourceProvider, searchResultAttributesVSMapper)
    }

    @Test
    fun `mapToViewState - calls attributes view state mapper and returns correct view state`() {
        // GIVEN
        val summarizedProduct = SummarizedProductFixtures.just()
        val formattedPrice = "mockedPrice"
        every { resourceProvider.getString(any(), any()) } returns formattedPrice
        // WHEN
        val viewState = searchResultItemVSMapper.mapToViewState(summarizedProduct)

        // THEN
        verify {
            resourceProvider.getString(
                R.string.product_formatted_price,
                summarizedProduct.price.roundToInt()
            )
            searchResultAttributesVSMapper.mapToViewState(summarizedProduct.attributes)
        }
        assert(viewState.productId == summarizedProduct.id)
        assert(viewState.imageUrl == summarizedProduct.thumbnailUrl)
        assert(viewState.title == summarizedProduct.title)
        assert(viewState.price == formattedPrice)
    }
}
