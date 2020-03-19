package francisco.visintini.mercadolibre.test.search.result

import android.content.Context
import francisco.visintini.mercadolibre.domain.entity.SummarizedProduct
import javax.inject.Inject

class SearchResultItemVSMapper @Inject constructor(context: Context) {
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
            "$${summarizedProduct.price}"
        )
}
