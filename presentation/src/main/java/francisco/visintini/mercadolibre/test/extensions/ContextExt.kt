package francisco.visintini.mercadolibre.test.extensions

import android.content.Context
import androidx.annotation.DimenRes
import kotlin.math.roundToInt

fun Context.convertDpToPixels(dp: Int): Int {
    return android.util.TypedValue.applyDimension(
        android.util.TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(),
        this.resources.displayMetrics
    ).roundToInt()
}