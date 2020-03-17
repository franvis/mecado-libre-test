package francisco.visintini.mercadolibre.domain.entity

data class Product(
    val id: String,
    val title: String,
    val price: Double,
    val currencyId: String,
    val initialQuantity: Int,
    val availableQuantity: Int,
    val soldQuantity: Int,
    val pictures: List<ProductPicture>
)
