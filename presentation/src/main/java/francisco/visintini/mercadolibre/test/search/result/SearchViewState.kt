package francisco.visintini.mercadolibre.test.search.result

import android.os.Parcelable
import francisco.visintini.mercadolibre.test.search.bar.SearchBar.ViewState as SearchBarViewState
import francisco.visintini.mercadolibre.test.search.result.SearchResultItem.ViewState
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchViewState(
    val searchBarViewState: SearchBarViewState = SearchBarViewState(),
    val searchContentViewState: SearchContentViewState
) : Parcelable

sealed class SearchContentViewState : Parcelable {
    @Parcelize
    data class Initial(val searchHistory: List<String>) : SearchContentViewState()
    @Parcelize
    data class Content(val searchResults: List<ViewState>) : SearchContentViewState()
    @Parcelize
    object Loading : SearchContentViewState()
    @Parcelize
    object Empty : SearchContentViewState()
    @Parcelize
    object Error : SearchContentViewState() // TODO Add inside different type of errors to show
}
