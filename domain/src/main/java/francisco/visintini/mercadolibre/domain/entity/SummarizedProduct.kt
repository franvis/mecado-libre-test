package francisco.visintini.mercadolibre.domain.entity

data class SummarizedProduct(
    val id: String,
    val siteId: String,
    val title: String,
    val price: Double,
    val availableQuantity: Int,
    val soldQuantity: Int,
    val thumbnailUrl: String,
    val shipping: ShippingInfo,
    val attributes: List<ProductAttribute>
)
