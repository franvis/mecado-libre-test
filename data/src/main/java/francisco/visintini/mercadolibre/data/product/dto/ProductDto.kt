package francisco.visintini.mercadolibre.data.product.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import francisco.visintini.mercadolibre.domain.entity.Product
import francisco.visintini.mercadolibre.domain.entity.SummarizedProduct

@JsonClass(generateAdapter = true)
internal data class ProductDto(
    val id: String,
    val title: String,
    val price: Double,
    @Json(name = "currency_id") val currencyId: String,
    @Json(name = "initial_quantity") val initialQuantity: Int,
    @Json(name = "available_quantity") val availableQuantity: Int,
    @Json(name = "sold_quantity") val soldQuantity: Int,
    val pictures: List<PictureDto>
) {

    fun mapToDomain() = Product(
        id = id,
        title = title,
        price = price,
        currencyId = currencyId,
        initialQuantity = initialQuantity,
        availableQuantity = availableQuantity,
        soldQuantity = soldQuantity,
        pictures = pictures.map { it.mapToDomain() }
    )
}
