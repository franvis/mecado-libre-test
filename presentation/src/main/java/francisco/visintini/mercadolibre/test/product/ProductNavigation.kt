package francisco.visintini.mercadolibre.test.product

sealed class ProductNavigation {
    class ToError(val message: String) : ProductNavigation()
}
