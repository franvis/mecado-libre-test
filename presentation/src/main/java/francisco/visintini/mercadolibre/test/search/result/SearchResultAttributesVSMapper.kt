package francisco.visintini.mercadolibre.test.search.result

import francisco.visintini.mercadolibre.domain.entity.ProductAttribute
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchResultAttributesVSMapper @Inject constructor() {

    fun mapToViewState(productAttributes: List<ProductAttribute>): SearchResultAttributes.ViewState {
        return SearchResultAttributes.ViewState(productAttributes.filter { it.valueName != null }.map {
            SearchResultAttributeItem.ViewState(
                it.name,
                it.valueName!!
            )
        })
    }
}
