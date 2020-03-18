package francisco.visintini.mercadolibre.test.search

import android.util.Log
import androidx.lifecycle.viewModelScope
import francisco.visintini.mercadolibre.domain.entity.ErrorEntity
import francisco.visintini.mercadolibre.domain.entity.Result.Error
import francisco.visintini.mercadolibre.domain.entity.Result.Success
import francisco.visintini.mercadolibre.domain.interactor.GetSearchResult
import francisco.visintini.mercadolibre.test.search.SearchIntent.BackPressed
import francisco.visintini.mercadolibre.test.search.SearchIntent.ClearSearch
import francisco.visintini.mercadolibre.test.search.SearchIntent.Search
import francisco.visintini.mercadolibre.test.search.SearchIntent.SearchFocus
import francisco.visintini.mercadolibre.test.search.SearchIntent.TextChanged
import francisco.visintini.mercadolibre.test.search.result.SearchContentViewState.Content
import francisco.visintini.mercadolibre.test.search.result.SearchContentViewState.Initial
import francisco.visintini.mercadolibre.test.search.result.SearchContentViewState.Loading
import francisco.visintini.mercadolibre.test.search.result.SearchResultItemVSMapper
import francisco.visintini.mercadolibre.test.search.result.SearchViewState
import francisco.visintini.mercadolibre.test.utils.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val getSearchResult: GetSearchResult,
    private val searchResultItemVSMapper: SearchResultItemVSMapper
) :
    BaseViewModel<SearchViewState>() {

    init {
        _viewState.value =
            SearchViewState(searchContentViewState = Initial(emptyList()))
    }

    fun handleIntent(intent: SearchIntent) {
        when (intent) {
            is Search -> handleSearch(intent)
            is TextChanged -> handleSearchTextChanged(intent)
            is SearchFocus -> handleSearchFocus(intent)
            is ClearSearch -> handleClearSearch()
            is BackPressed -> handleBackPressed()
        }
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

    private fun handleBackPressed() {
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
                                    result.t.results.map { prod ->
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

    private fun updateViewState(reducer: (SearchViewState) -> SearchViewState) {
        _viewState.value?.run { _viewState.value = reducer(this) }
    }
}