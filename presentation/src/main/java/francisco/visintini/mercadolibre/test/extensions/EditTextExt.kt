package francisco.visintini.mercadolibre.test.extensions

import android.widget.EditText

/**
 * This extension function is meant to be used when clearing the text of an edit text is needed.
 * Additionally it will not trigger changed text events by the TextWatcher
 */
fun EditText.clearText() {
    this.text.apply { replace(0, length, "") }
}
