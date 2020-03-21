package francisco.visintini.mercadolibre.domain.entity

data class Product(
    val id: String,
    val title: String,
    val price: Double,
    val initialQuantity: Int,
    val availableQuantity: Int,
    val soldQuantity: Int,
    val warrantyText: String?,
    val condition: Condition,
    val pictures: List<ProductPicture>
)

enum class Condition {
    NEW, USED, UNDEFINED
}
