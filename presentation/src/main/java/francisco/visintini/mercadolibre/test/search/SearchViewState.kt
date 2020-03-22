package francisco.visintini.mercadolibre.test.search

import android.os.Parcelable
import francisco.visintini.mercadolibre.test.search.bar.SearchBar.ViewState as SearchBarViewState
import francisco.visintini.mercadolibre.test.search.result.SearchResultItem.ViewState
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchViewState(
    val searchBarViewState: SearchBarViewState = SearchBarViewState(),
    val contentState: ContentState
) : Parcelable

sealed class ContentState : Parcelable {
    @Parcelize
    data class Initial(val searchHistory: List<String>) : ContentState()
    @Parcelize
    data class Content(val searchResults: List<ViewState>) : ContentState()
    @Parcelize
    object Loading : ContentState()
    @Parcelize
    object Empty : ContentState()

    sealed class Error : ContentState() {
        @Parcelize
        object NetworkErrorRetry : Error()
        @Parcelize
        object UnknownError : Error()
    }
}
