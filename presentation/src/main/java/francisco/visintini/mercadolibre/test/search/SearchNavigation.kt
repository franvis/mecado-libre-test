package francisco.visintini.mercadolibre.test.search

sealed class SearchNavigation {
    class ToProduct(val productId: String) : SearchNavigation()
    class ToError(val message: String) : SearchNavigation()
}
