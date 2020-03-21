package francisco.visintini.mercadolibre.data.product.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import francisco.visintini.mercadolibre.domain.entity.ProductPicture

@JsonClass(generateAdapter = true)
data class PictureDto(
    val id: String,
    @Json(name = "secure_url") val secureUrl: String
) {
    fun mapToDomain() = ProductPicture(id = id, url = secureUrl)
}
