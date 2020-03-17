package francisco.visintini.mercadolibre.data.search.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import francisco.visintini.mercadolibre.domain.entity.SummarizedProduct

@JsonClass(generateAdapter = true)
internal data class SummarizedProductDto(
    val id: String,
    @Json(name = "site_id") val siteId: String,
    val title: String,
    val price: Double,
    @Json(name = "currency_id") val currencyId: String,
    @Json(name = "available_quantity") val availableQuantity: Int,
    @Json(name = "sold_quantity") val soldQuantity: Int,
    @Json(name = "thumbnail") val thumbnailUrl: String,
    val shipping: ShippingDto,
    val attributes: List<AttributeDto>
) {
    fun mapToDomain() =
        SummarizedProduct(
            id = id,
            siteId = siteId,
            title = title,
            price = price,
            currencyId = currencyId,
            availableQuantity = availableQuantity,
            soldQuantity = soldQuantity,
            thumbnailUrl = thumbnailUrl.replace("http", "https"),
            shipping = shipping.mapToDomain(),
            attributes = attributes.map { it.mapToDomain() }
        )
}
