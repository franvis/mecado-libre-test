package francisco.visintini.mercadolibre.data.product.dto

import com.squareup.moshi.JsonClass
import francisco.visintini.mercadolibre.domain.entity.ProductPicture

@JsonClass(generateAdapter = true)
data class PictureDto(
    val id: String,
    val url: String
) {
    fun mapToDomain() = ProductPicture(id = id, url = url)
}
