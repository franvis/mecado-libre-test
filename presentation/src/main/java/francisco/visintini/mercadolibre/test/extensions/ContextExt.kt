package francisco.visintini.mercadolibre.test.extensions

import android.content.Context
import kotlin.math.roundToInt

/**
 * Converts a dp value into pixels.
 *
 * @param dp value to be converted
 */
fun Context.convertDpToPixels(dp: Int): Int {
    return android.util.TypedValue.applyDimension(
        android.util.TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(),
        this.resources.displayMetrics
    ).roundToInt()
}
