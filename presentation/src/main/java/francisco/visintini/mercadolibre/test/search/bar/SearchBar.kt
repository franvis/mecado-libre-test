package francisco.visintini.mercadolibre.test.search.bar

import android.content.Context
import android.os.Parcelable
import android.text.InputType
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxbinding2.view.focusChanges
import com.jakewharton.rxbinding2.view.keys
import com.jakewharton.rxbinding2.widget.editorActionEvents
import com.jakewharton.rxbinding2.widget.textChangeEvents
import francisco.visintini.mercadolibre.test.R
import francisco.visintini.mercadolibre.test.extensions.clearText
import francisco.visintini.mercadolibre.test.extensions.hideKeyboard
import francisco.visintini.mercadolibre.test.extensions.showKeyboard
import francisco.visintini.mercadolibre.test.search.bar.SearchBar.SearchBarIntent.BackPressed
import francisco.visintini.mercadolibre.test.search.bar.SearchBar.SearchBarIntent.ClearSearch
import francisco.visintini.mercadolibre.test.search.bar.SearchBar.SearchBarIntent.Search
import francisco.visintini.mercadolibre.test.search.bar.SearchBar.SearchBarIntent.SearchFocus
import francisco.visintini.mercadolibre.test.search.bar.SearchBar.SearchBarIntent.TextChanged
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Predicate
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.merge_search_bar.view.*

class SearchBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.merge_search_bar, this)
    }

    fun render(viewState: ViewState) {
        view_search_bar_image_clear.isVisible = viewState.isCancelable
        view_search_bar_image_search.isVisible = !viewState.isCancelable
        view_search_bar_back.isVisible = viewState.isFocused
        if (!viewState.isFocused) {
            view_search_bar_edit_text.clearFocus()
            view_search_bar_edit_text.hideKeyboard()
        } else {
            view_search_bar_edit_text.requestFocus()
            view_search_bar_edit_text.showKeyboard()
        }

        if (!viewState.isCancelable) {
            view_search_bar_edit_text.clearText()
        }

        if (view_search_bar_edit_text.text.toString() != viewState.query) {
            view_search_bar_edit_text.setText(viewState.query)
        }

        val inputType = if (viewState.allowInput) InputType.TYPE_CLASS_TEXT else InputType.TYPE_NULL
        view_search_bar_edit_text.inputType = inputType
    }

    fun getIntents(): Observable<SearchBarIntent> {
        val keyEnterIntents = view_search_bar_edit_text
            .keys(Predicate { it.keyCode == KeyEvent.KEYCODE_ENTER })
            .filter { view_search_bar_edit_text.text.toString().isNotEmpty() }
            .map { Search(view_search_bar_edit_text.text.toString()) }

        val editorActionIntents = view_search_bar_edit_text
            .editorActionEvents()
            .filter {
                it.actionId() == EditorInfo.IME_ACTION_SEARCH &&
                    view_search_bar_edit_text.text.toString().isNotEmpty()
            }
            .map { Search(view_search_bar_edit_text.text.toString()) }

        val searchIntents = Observable.merge(keyEnterIntents, editorActionIntents)

        val textChangeIntents = view_search_bar_edit_text.textChangeEvents()
            .observeOn(AndroidSchedulers.mainThread())
            .filter {
                (focusedChild as? ConstraintLayout)?.focusedChild == view_search_bar_edit_text
            }
            .map { TextChanged(it.text().toString()) }

        val clearIntents = view_search_bar_image_clear.clicks().map { ClearSearch }

        val focusChangeIntents = view_search_bar_edit_text.focusChanges().map { SearchFocus(it) }

        val cancelSearchIntents = view_search_bar_back.clicks().map { BackPressed }

        return Observable.mergeArray(
            focusChangeIntents, searchIntents, cancelSearchIntents, textChangeIntents, clearIntents
        )
    }

    @Parcelize
    data class ViewState(
        val isFocused: Boolean = false,
        val isCancelable: Boolean = false,
        val query: String = "",
        val allowInput: Boolean = true
    ) : Parcelable

    sealed class SearchBarIntent {
        data class SearchFocus(val focused: Boolean) : SearchBarIntent()
        data class Search(val query: String) : SearchBarIntent()
        data class TextChanged(val currentQuery: String) : SearchBarIntent()
        object ClearSearch : SearchBarIntent()
        object BackPressed : SearchBarIntent()
    }
}
