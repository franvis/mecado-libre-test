package francisco.visintini.mercadolibre.test.search.result

import francisco.visintini.mercadolibre.test.search.bar.SearchBar.ViewState as SearchBarViewState
import francisco.visintini.mercadolibre.test.search.result.SearchResultItem.ViewState

data class SearchViewState(
    val searchBarViewState: SearchBarViewState = SearchBarViewState(),
    val searchContentViewState: SearchContentViewState
)

sealed class SearchContentViewState {
    data class Initial(val searchHistory: List<String>): SearchContentViewState()
    data class Content(val searchResults: List<ViewState>): SearchContentViewState()
    object Loading: SearchContentViewState()
    object Empty: SearchContentViewState()
    object Error: SearchContentViewState() // TODO Add inside different type of errors to show
}
