package francisco.visintini.mercadolibre.test.extensions

import android.widget.EditText

/**
 * Clears the text of an edit text without triggering changed text events by the TextWatcher
 */
fun EditText.clearText() {
    this.text.apply { replace(0, length, "") }
}
