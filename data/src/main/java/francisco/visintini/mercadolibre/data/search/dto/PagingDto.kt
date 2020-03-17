package francisco.visintini.mercadolibre.data.search.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import francisco.visintini.mercadolibre.domain.entity.PagingInfo

@JsonClass(generateAdapter = true)
internal data class PagingDto(
    val total: Int,
    val offset: Int,
    val limit: Int,
    @Json(name = "primary_results") val primaryResults: Int
) {
    fun mapToDomain() = PagingInfo(
        total = total,
        offset = offset,
        limit = limit,
        primaryResults = primaryResults
    )
}
