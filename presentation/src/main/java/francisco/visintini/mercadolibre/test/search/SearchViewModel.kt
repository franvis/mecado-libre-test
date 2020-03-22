package francisco.visintini.mercadolibre.test.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import francisco.visintini.mercadolibre.domain.entity.ErrorEntity
import francisco.visintini.mercadolibre.domain.entity.Result.Error
import francisco.visintini.mercadolibre.domain.entity.Result.Success
import francisco.visintini.mercadolibre.domain.interactor.GetSearchResult
import francisco.visintini.mercadolibre.test.di.ViewModelFactory
import francisco.visintini.mercadolibre.test.search.SearchIntent.ClearSearch
import francisco.visintini.mercadolibre.test.search.SearchIntent.Search
import francisco.visintini.mercadolibre.test.search.SearchIntent.SearchBarBackPressed
import francisco.visintini.mercadolibre.test.search.SearchIntent.SearchFocus
import francisco.visintini.mercadolibre.test.search.SearchIntent.SearchResultTapped
import francisco.visintini.mercadolibre.test.search.SearchIntent.TextChanged
import francisco.visintini.mercadolibre.test.search.SearchNavigation.ToProduct
import francisco.visintini.mercadolibre.test.search.result.SearchContentViewState.Content
import francisco.visintini.mercadolibre.test.search.result.SearchContentViewState.Initial
import francisco.visintini.mercadolibre.test.search.result.SearchContentViewState.Loading
import francisco.visintini.mercadolibre.test.search.result.SearchResultItemVSMapper
import francisco.visintini.mercadolibre.test.search.result.SearchViewState
import francisco.visintini.mercadolibre.test.utils.BaseSavedStateViewModel
import francisco.visintini.mercadolibre.test.utils.SingleLiveEvent
import javax.inject.Inject
import kotlinx.coroutines.launch

class SearchViewModel(
    override val handle: SavedStateHandle,
    private val getSearchResult: GetSearchResult,
    private val searchResultItemVSMapper: SearchResultItemVSMapper
) : BaseSavedStateViewModel<SearchViewState>(handle) {

    private val _navigator = SingleLiveEvent<SearchNavigation>()
    val navigator: LiveData<SearchNavigation>
        get() = _navigator

    fun start() {
        _viewState.value = getSavedState().value?.let { it } ?: SearchViewState(
            searchContentViewState = Initial(emptyList())
        )
    }

    fun handleIntent(intent: SearchIntent) {
        when (intent) {
            is Search -> handleSearch(intent)
            is TextChanged -> handleSearchTextChanged(intent)
            is SearchFocus -> handleSearchFocus(intent)
            is ClearSearch -> handleClearSearch()
            is SearchBarBackPressed -> handleSearchBarBackPressed()
            is SearchResultTapped -> handleSearchResultTapped(intent)
        }
    }

    private fun handleSearchResultTapped(intent: SearchResultTapped) {
        _navigator.value = ToProduct(intent.productId)
    }

    private fun handleSearchTextChanged(intent: TextChanged) {
        return updateViewState { oldState ->
            oldState.copy(
                searchBarViewState = oldState.searchBarViewState.copy(
                    isCancelable = intent.currentQuery.isNotEmpty(),
                    query = intent.currentQuery
                )
            )
        }
    }

    private fun handleSearchBarBackPressed() {
        return updateViewState { oldState ->
            oldState.copy(
                searchBarViewState = oldState.searchBarViewState.copy(isFocused = false)
            )
        }
    }

    private fun handleClearSearch() {
        return updateViewState { oldState ->
            oldState.copy(
                searchBarViewState = oldState.searchBarViewState.copy(
                    isFocused = true,
                    isCancelable = false,
                    allowInput = true,
                    query = ""
                ),
                searchContentViewState = Initial(emptyList()) // Add History
            )
        }
    }

    private fun handleSearchFocus(intent: SearchFocus) {
        return updateViewState { oldState ->
            if (oldState.searchBarViewState.isFocused != intent.focused) {
                oldState.copy(
                    searchBarViewState = oldState.searchBarViewState.copy(isFocused = intent.focused)
                )
            } else oldState
        }
    }

    private fun handleSearch(intent: Search) {
        updateViewState { oldState ->
            oldState.copy(
                searchBarViewState = oldState.searchBarViewState.copy(
                    isFocused = false,
                    isCancelable = true,
                    allowInput = true,
                    query = intent.query
                ), searchContentViewState = Loading
            )
        }
        viewModelScope.launch {
            try {
                when (val result = getSearchResult.execute(intent.query)) {
                    is Success -> {
                        updateViewState { oldState ->
                            oldState.copy(
                                searchContentViewState = Content(
                                    result.result.results.map { prod ->
                                        searchResultItemVSMapper.mapToViewState(prod)
                                    }
                                )
                            )
                        }
                    }

                    is Error -> {
                        // TODO Update UI with error depending on the type
                        when (result.error) {
                            is ErrorEntity.UnknownError -> {
                                Log.e("SHOW ERROR HERE", "SHOW ERROR HERE")
                            }
                        }
                    }
                }
            } catch (exception: Exception) {
                // TODO Track presentation exception here and show unexpected error message to user
                Log.e("Fran", "Fran")
            }
        }
    }

    class Factory @Inject constructor(
        private val getSearchResult: GetSearchResult,
        private val searchResultItemVSMapper: SearchResultItemVSMapper
    ) : ViewModelFactory<SearchViewModel> {
        override fun create(handle: SavedStateHandle): SearchViewModel {
            return SearchViewModel(handle, getSearchResult, searchResultItemVSMapper)
        }
    }
}
