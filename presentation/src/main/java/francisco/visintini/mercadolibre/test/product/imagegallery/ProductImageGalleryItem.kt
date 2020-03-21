package francisco.visintini.mercadolibre.test.product.imagegallery

import android.os.Parcelable
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import francisco.visintini.mercadolibre.test.R
import francisco.visintini.mercadolibre.test.extensions.load
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.item_product_gallery.*

data class ProductImageGalleryItem(val viewState: ViewState) : Item() {
    override fun getLayout(): Int = R.layout.item_product_gallery

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.view_product_gallery_image.load(viewState.imageUrl)
    }

    @Parcelize
    data class ViewState(val imageUrl: String) : Parcelable
}
