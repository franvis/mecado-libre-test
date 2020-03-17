package francisco.visintini.mercadolibre.data.product.service

import francisco.visintini.mercadolibre.data.product.dto.ProductDto
import francisco.visintini.mercadolibre.data.search.dto.SearchDto
import retrofit2.http.GET
import retrofit2.http.Query

internal interface ProductService {

    @GET("/items")
    suspend fun getProduct(@Query("id") productId: String): ProductDto
}
