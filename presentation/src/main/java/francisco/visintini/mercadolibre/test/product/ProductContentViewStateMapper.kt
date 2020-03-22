package francisco.visintini.mercadolibre.test.product

import francisco.visintini.mercadolibre.domain.entity.Condition
import francisco.visintini.mercadolibre.domain.entity.Product
import francisco.visintini.mercadolibre.test.R
import francisco.visintini.mercadolibre.test.product.ProductViewState.ContentState.Content
import francisco.visintini.mercadolibre.test.product.imagegallery.ProductImageGalleryItemVSMapper
import francisco.visintini.mercadolibre.test.product.imagegallery.ProductImageGalleryViewState
import francisco.visintini.mercadolibre.test.utils.ResourceProvider
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.roundToInt

@Singleton
class ProductContentViewStateMapper @Inject constructor(
    private val productImageGalleryItemVSMapper: ProductImageGalleryItemVSMapper,
    private val resourceProvider: ResourceProvider
) {
    fun transform(product: Product) =
        with(product) {
            Content(
                title,
                imageGalleryViewState = ProductImageGalleryViewState(
                    productImageGalleryItemViewStates = pictures.map { productPic ->
                        productImageGalleryItemVSMapper.transform(productPic)
                    }),
                price = resourceProvider.getString(
                    R.string.product_formatted_price,
                    price.roundToInt()
                ),
                availability = resourceProvider.getString(
                    R.string.product_availability,
                    availableQuantity,
                    soldQuantity
                ),
                warranty = warrantyText,
                condition = when (condition) {
                    Condition.NEW -> resourceProvider.getString(R.string.product_condition_new)
                    Condition.USED -> resourceProvider.getString(R.string.product_condition_used)
                    else -> null
                }
            )
        }
}
