package francisco.visintini.mercadolibre.domain.entity

data class PagingInfo (
    val total: Int,
    val offset: Int,
    val limit: Int,
    val primaryResults: Int
)
