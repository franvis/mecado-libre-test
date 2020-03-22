package francisco.visintini.mercadolibre.test.messages

import android.view.View

interface MessageManager {
    fun showError(view: View, errorMessage: ErrorMessage)
}
