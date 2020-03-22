package francisco.visintini.mercadolibre.test.product

import android.os.Parcelable
import francisco.visintini.mercadolibre.test.product.imagegallery.ProductImageGalleryViewState
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductViewState(
    val productId: String,
    val productContentState: ContentState
) : Parcelable {

    sealed class ContentState : Parcelable {
        @Parcelize
        object Initial : ContentState()

        @Parcelize
        object Error : ContentState()

        @Parcelize
        object Loading : ContentState()

        @Parcelize
        data class Content(
            val title: String,
            val imageGalleryViewState: ProductImageGalleryViewState,
            val price: String,
            val availability: String,
            val warranty: String?,
            val condition: String?
        ) : ContentState()
    }
}

@ExperimentalContracts
fun ProductViewState.ContentState.isContent(): Boolean {
    contract {
        returns(true) implies (this@isContent is ProductViewState.ContentState.Content)
    }
    return this is ProductViewState.ContentState.Content
}
