package francisco.visintini.mercadolibre.data.search.service

import francisco.visintini.mercadolibre.data.search.dto.SearchDto
import retrofit2.http.GET
import retrofit2.http.Query

internal interface SearchService {

    @GET("/sites/MLA/search")
    suspend fun search(@Query("q") query: String, @Query("offset") page: Int): SearchDto
}
