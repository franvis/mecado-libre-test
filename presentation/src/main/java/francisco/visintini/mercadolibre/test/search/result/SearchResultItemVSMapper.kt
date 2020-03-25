package francisco.visintini.mercadolibre.test.search.result

import francisco.visintini.mercadolibre.domain.entity.SummarizedProduct
import francisco.visintini.mercadolibre.test.R
import francisco.visintini.mercadolibre.test.utils.ResourceProvider
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.roundToInt

@Singleton
class SearchResultItemVSMapper @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val searchResultAttributesVSMapper: SearchResultAttributesVSMapper
) {
    fun mapToViewState(summarizedProduct: SummarizedProduct) =
        SearchResultItem.ViewState(
            summarizedProduct.thumbnailUrl,
            summarizedProduct.title,
            summarizedProduct.id,
            searchResultAttributesVSMapper.mapToViewState(summarizedProduct.attributes),
            resourceProvider.getString(
                R.string.product_formatted_price,
                summarizedProduct.price.roundToInt()
            )
        )
}
