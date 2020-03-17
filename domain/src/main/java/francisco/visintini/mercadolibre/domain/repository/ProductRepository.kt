package francisco.visintini.mercadolibre.domain.repository

import francisco.visintini.mercadolibre.domain.entity.Product
import francisco.visintini.mercadolibre.domain.entity.Result

interface ProductRepository {
    suspend fun getProduct(productId: String): Result<Product>
}