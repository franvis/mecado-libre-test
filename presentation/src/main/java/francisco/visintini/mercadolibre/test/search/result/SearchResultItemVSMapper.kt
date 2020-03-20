package francisco.visintini.mercadolibre.test.search.result

import francisco.visintini.mercadolibre.domain.entity.SummarizedProduct
import francisco.visintini.mercadolibre.test.R
import francisco.visintini.mercadolibre.test.utils.ResourceProvider
import javax.inject.Inject

class SearchResultItemVSMapper @Inject constructor(private val resourceProvider: ResourceProvider) {
    fun mapToViewState(summarizedProduct: SummarizedProduct) =
        SearchResultItem.ViewState(
            summarizedProduct.thumbnailUrl,
            summarizedProduct.title,
            summarizedProduct.id,
            SearchResultAttributes.ViewState(
                summarizedProduct.attributes.filter { it.valueName != null }.map { prodAttribute ->
                    SearchResultAttributeItem.ViewState(
                        name = prodAttribute.name,
                        description = prodAttribute.valueName!!
                    )
                }
            ),
            resourceProvider.getString(
                R.string.search_result_item_formatted_price,
                summarizedProduct.price
            )
        )
}
