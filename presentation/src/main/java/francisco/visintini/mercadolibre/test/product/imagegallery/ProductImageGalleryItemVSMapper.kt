package francisco.visintini.mercadolibre.test.product.imagegallery

import francisco.visintini.mercadolibre.domain.entity.ProductPicture
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductImageGalleryItemVSMapper @Inject constructor() {

    fun transform(productPicture: ProductPicture) =
        ProductImageGalleryItem.ViewState(productPicture.url)
}
