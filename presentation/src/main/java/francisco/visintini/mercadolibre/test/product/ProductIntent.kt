package francisco.visintini.mercadolibre.test.product

sealed class ProductIntent {
    data class ImageGalleryPositionChanged(val newPosition: Int) : ProductIntent()
}
