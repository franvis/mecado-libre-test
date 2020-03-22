package francisco.visintini.mercadolibre.test.product.imagegallery

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductImageGalleryViewState(
    val imagePosition: Int = 0,
    val productImageGalleryItemViewStates: List<ProductImageGalleryItem.ViewState>
) : Parcelable
