package francisco.visintini.mercadolibre.test.product

import android.os.Parcelable
import francisco.visintini.mercadolibre.test.product.imagegallery.ProductImageGalleryViewState
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductViewState(
    val productId: String,
    val productState: State
) : Parcelable {

    sealed class State : Parcelable {
        @Parcelize
        object Initial : State()

        @Parcelize
        object Error : State()

        @Parcelize
        object Loading : State()

        @Parcelize
        data class Content(
            val title: String,
            val imageGalleryViewState: ProductImageGalleryViewState,
            val price: String,
            val availability: String,
            val warranty: String?,
            val condition: String?
        ) : State()
    }
}

@ExperimentalContracts
fun ProductViewState.State.isContent(): Boolean {
    contract {
        returns(true) implies (this@isContent is ProductViewState.State.Content)
    }
    return this is ProductViewState.State.Content
}
