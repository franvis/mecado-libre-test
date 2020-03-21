package francisco.visintini.mercadolibre.test.product

sealed class ProductIntent {
    data class LoadProduct(val productId: String) : ProductIntent()
}
