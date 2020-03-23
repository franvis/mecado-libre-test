package francisco.visintini.mercadolibre.test.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide

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
