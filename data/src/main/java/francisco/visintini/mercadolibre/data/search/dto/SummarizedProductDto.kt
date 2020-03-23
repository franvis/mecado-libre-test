package francisco.visintini.mercadolibre.data.search.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import francisco.visintini.mercadolibre.domain.entity.SummarizedProduct

@JsonClass(generateAdapter = true)
internal data class SummarizedProductDto(
    val id: String,
    @Json(name = "site_id") val siteId: String,
    val title: String,
    val price: Double?,
    @Json(name = "available_quantity") val availableQuantity: Int,
    @Json(name = "sold_quantity") val soldQuantity: Int,
    @Json(name = "thumbnail") val thumbnailUrl: String,
    val shipping: ShippingDto,
    val attributes: List<AttributeDto>
) {
    /**
     * Maps a summarized product dto to a domain one.
     */
    fun mapToDomain() =
        // We need to check this because sometimes price is null in the response...
        // For example https://api.mercadolibre.com/sites/MLA/search?q=sas&offset=0
        // The second product(id: MLA817584418) has both price and original_price as null
        price?.let {
            SummarizedProduct(
                id = id,
                siteId = siteId,
                title = title,
                price = it,
                availableQuantity = availableQuantity,
                soldQuantity = soldQuantity,
                thumbnailUrl = thumbnailUrl.replace("http", "https"),
                shipping = shipping.mapToDomain(),
                attributes = attributes.map { attributeDto -> attributeDto.mapToDomain() }
            )
        }
}
