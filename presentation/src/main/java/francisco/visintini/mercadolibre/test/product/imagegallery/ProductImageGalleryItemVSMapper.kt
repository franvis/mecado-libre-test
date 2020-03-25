package francisco.visintini.mercadolibre.test.product.imagegallery

import francisco.visintini.mercadolibre.domain.entity.ProductPicture
import francisco.visintini.mercadolibre.test.product.imagegallery.ProductImageGalleryItem.ViewState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductImageGalleryItemVSMapper @Inject constructor() {

    fun mapToViewState(productPicture: ProductPicture) = ViewState(productPicture.url)
}
