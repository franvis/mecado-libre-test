package francisco.visintini.mercadolibre.data.product.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import francisco.visintini.mercadolibre.domain.entity.Condition.NEW
import francisco.visintini.mercadolibre.domain.entity.Condition.UNDEFINED
import francisco.visintini.mercadolibre.domain.entity.Condition.USED
import francisco.visintini.mercadolibre.domain.entity.Product

@JsonClass(generateAdapter = true)
internal data class ProductDto(
    val id: String,
    val title: String,
    val price: Double,
    @Json(name = "initial_quantity") val initialQuantity: Int,
    @Json(name = "available_quantity") val availableQuantity: Int,
    @Json(name = "sold_quantity") val soldQuantity: Int,
    val pictures: List<PictureDto>,
    val warranty: String?,
    val condition: String
) {

    fun mapToDomain() = Product(
        id = id,
        title = title,
        price = price,
        initialQuantity = initialQuantity,
        availableQuantity = availableQuantity,
        soldQuantity = soldQuantity,
        pictures = pictures.map { it.mapToDomain() },
        warrantyText = warranty,
        condition = when (condition) {
            "new" -> NEW
            "used" -> USED
            else -> UNDEFINED
        }
    )
}
