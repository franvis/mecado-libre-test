package francisco.visintini.mercadolibre.data.product.repository

import francisco.visintini.mercadolibre.data.product.service.ProductService
import francisco.visintini.mercadolibre.data.utils.wrapDataCall
import francisco.visintini.mercadolibre.domain.entity.Product
import francisco.visintini.mercadolibre.domain.entity.Result
import francisco.visintini.mercadolibre.domain.repository.ErrorHandler
import francisco.visintini.mercadolibre.domain.repository.ProductRepository
import javax.inject.Inject

internal data class ProductRepositoryImpl @Inject constructor(
    private val productService: ProductService,
    private val errorHandler: ErrorHandler
) : ProductRepository {
    override suspend fun getProduct(productId: String): Result<Product> {
        return wrapDataCall(errorHandler) {
            productService.getProduct(productId).mapToDomain()
        }
    }
}
