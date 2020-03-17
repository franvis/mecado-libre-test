package francisco.visintini.mercadolibre.data.search.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import francisco.visintini.mercadolibre.domain.entity.SearchResult

@JsonClass(generateAdapter = true)
internal data class SearchDto(
    @Json(name = "site_id") val siteId: String,
    val query: String,
    val paging: PagingDto,
    val results: List<SummarizedProductDto>
    // Not mapping the rest of the fields since they are not being used for this sample app
) {
    fun mapToDomain() =
        SearchResult(
            siteId = siteId,
            query = query,
            paging = paging.mapToDomain(),
            results = results.map { it.mapToDomain() }
        )
}
