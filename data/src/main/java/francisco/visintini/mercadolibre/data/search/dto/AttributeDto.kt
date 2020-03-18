package francisco.visintini.mercadolibre.data.search.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import francisco.visintini.mercadolibre.domain.entity.ProductAttribute

@JsonClass(generateAdapter = true)
internal data class AttributeDto(
    val id: String,
    val name: String,
    @Json(name = "value_name") val valueName: String?
) {
    fun mapToDomain() = ProductAttribute(id = id, name = name, valueName = valueName)
}
