package francisco.visintini.mercadolibre.test.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade

/**
 * Loads an image on an ImageView.
 *
 * @param url Url of the high resolution image to load
 * @param size Size of the image to load (this will always result in a request for a square image)
 */
fun ImageView.load(url: String?, size: Int? = null) {
    var request = Glide.with(this.context).load(url).fitCenter()

    if (size != null) {
        request = request.override(size)
    }

    request.into(this)
}

/**
 * Loads an image and the respective thumbnail on an ImageView.
 *
 * @param url Url of the high resolution image to load
 * @param thumbnailUrl Url of the low resolution image to use as a thumbnail
 */
fun ImageView.loadWithThumbnail(url: String?, thumbnailUrl: String?) {
    val thumbnailRequest = Glide.with(context).load(thumbnailUrl).fitCenter()

    Glide.with(context).load(url)
        .transition(withCrossFade())
        .thumbnail(thumbnailRequest)
        .fitCenter()
        .into(this)
}
