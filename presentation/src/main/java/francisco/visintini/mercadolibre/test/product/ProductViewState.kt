package francisco.visintini.mercadolibre.test.product

import android.os.Parcelable
import francisco.visintini.mercadolibre.test.product.imagegallery.ProductImageGalleryItem
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductViewState(
    val productId: String,
    val productContentViewState: ContentViewState
) : Parcelable {

    sealed class ContentViewState : Parcelable {
        @Parcelize
        object Initial : ContentViewState()

        @Parcelize
        object Error : ContentViewState()

        @Parcelize
        object Loading : ContentViewState()

        @Parcelize
        data class Content(
            val title: String,
            val imageGalleryViewState: List<ProductImageGalleryItem.ViewState>,
            val price: String,
            val availability: String,
            val warranty: String?,
            val condition: String?
        ) : ContentViewState()
    }
}
