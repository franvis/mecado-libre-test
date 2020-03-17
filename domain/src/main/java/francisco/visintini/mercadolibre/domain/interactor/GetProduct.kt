package francisco.visintini.mercadolibre.domain.interactor

import francisco.visintini.mercadolibre.domain.entity.Product
import francisco.visintini.mercadolibre.domain.entity.Result
import francisco.visintini.mercadolibre.domain.repository.ProductRepository
import javax.inject.Inject

class GetProduct @Inject constructor(private val productRepository: ProductRepository) {

    suspend fun execute(productId: String): Result<Product> {
        return productRepository.getProduct(productId)
    }
}
