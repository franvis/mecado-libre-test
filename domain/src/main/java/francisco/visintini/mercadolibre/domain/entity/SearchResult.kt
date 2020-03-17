package francisco.visintini.mercadolibre.domain.entity

data class SearchResult(
    val siteId: String,
    val query: String,
    val paging: PagingInfo,
    val results: List<SummarizedProduct>
)
