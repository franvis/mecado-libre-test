package francisco.visintini.mercadolibre.test.utils

import android.content.Context
import androidx.annotation.StringRes
import javax.inject.Inject

class ResourceProvider @Inject constructor(val context: Context) {
    @Suppress("SpreadOperator")
    fun getString(@StringRes id: Int, vararg formatArgs: Any) =
        context.getString(id, *formatArgs)
}
