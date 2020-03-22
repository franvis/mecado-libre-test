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
import francisco.visintini.mercadolibre.test.search.SearchIntent.NetworkErrorRetryTapped
import francisco.visintini.mercadolibre.test.search.SearchIntent.Search
import francisco.visintini.mercadolibre.test.search.SearchIntent.SearchBarBackPressed
import francisco.visintini.mercadolibre.test.search.SearchIntent.SearchFocus
import francisco.visintini.mercadolibre.test.search.SearchIntent.SearchResultTapped
import francisco.visintini.mercadolibre.test.search.SearchIntent.TextChanged
import francisco.visintini.mercadolibre.test.search.SearchNavigation.ToProduct
import francisco.visintini.mercadolibre.test.search.result.ContentState
import francisco.visintini.mercadolibre.test.search.result.ContentState.Content
import francisco.visintini.mercadolibre.test.search.result.ContentState.Empty
import francisco.visintini.mercadolibre.test.search.result.ContentState.Initial
import francisco.visintini.mercadolibre.test.search.result.ContentState.Loading
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

    private var lastIntent: SearchIntent? = null

    private val _navigator = SingleLiveEvent<SearchNavigation>()
    val navigator: LiveData<SearchNavigation>
        get() = _navigator

    fun start() {
        _viewState.value = getSavedState().value?.let { it } ?: SearchViewState(
            contentContentState = Initial(emptyList())
        )
        lastIntent?.let { handleIntent(it) }
    }

    fun handleIntent(intent: SearchIntent) {
        when (intent) {
            is Search -> handleSearch(intent)
            is TextChanged -> handleSearchTextChanged(intent)
            is SearchFocus -> handleSearchFocus(intent)
            is ClearSearch -> handleClearSearch()
            is SearchBarBackPressed -> handleSearchBarBackPressed()
            is SearchResultTapped -> handleSearchResultTapped(intent)
            is NetworkErrorRetryTapped -> lastIntent?.let { handleIntent(it) }
        }
    }

    private fun handleSearchResultTapped(intent: SearchResultTapped) {
        _navigator.value = ToProduct(intent.productId)
    }

    private fun handleSearchTextChanged(intent: TextChanged) {
        if (intent.currentQuery != _viewState.value?.searchBarViewState?.query) {
            updateViewState { oldState ->
                oldState.copy(
                    searchBarViewState = oldState.searchBarViewState.copy(
                        isCancelable = intent.currentQuery.isNotEmpty(),
                        query = intent.currentQuery
                    )
                )
            }
        }
    }

    private fun handleSearchBarBackPressed() {
        updateViewState { oldState ->
            oldState.copy(
                searchBarViewState = oldState.searchBarViewState.copy(isFocused = false)
            )
        }
    }

    private fun handleClearSearch() {
        lastIntent = null
        updateViewState { oldState ->
            oldState.copy(
                searchBarViewState = oldState.searchBarViewState.copy(
                    isFocused = true,
                    isCancelable = false,
                    allowInput = true,
                    query = ""
                ),
                contentContentState = Initial(emptyList()) // Add History
            )
        }
    }

    private fun handleSearchFocus(intent: SearchFocus) {
        updateViewState { oldState ->
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
                ), contentContentState = Loading
            )
        }
        viewModelScope.launch {
            try {
                when (val result = getSearchResult.execute(intent.query)) {
                    is Success -> {
                        updateViewState { oldState ->
                            val searchResults = result.result.results
                            oldState.copy(
                                contentContentState = if (searchResults.isEmpty()) {
                                    Empty
                                } else {
                                    Content(
                                        searchResults.map { prod ->
                                            searchResultItemVSMapper.mapToViewState(prod)
                                        }
                                    )
                                }
                            )
                        }
                        lastIntent = null
                    }

                    is Error -> {
                        lastIntent = intent
                        updateViewStateForError(result.error)
                    }
                }
            } catch (exception: Exception) {
                updateViewStateForError()
                // TODO Track here presentation layer exceptions and log them
                Log.e("Fran", "Fran")
            }
        }
    }

    private fun updateViewStateForError(error: ErrorEntity = ErrorEntity.UnknownError) {
        updateViewState { oldState ->
            when (error) {
                is ErrorEntity.NetworkError, ErrorEntity.ServiceUnavailable -> {
                    oldState.copy(
                        contentContentState = ContentState.Error.NetworkErrorRetry
                    )
                }
                is ErrorEntity.NotFound -> oldState.copy(
                    contentContentState = Empty
                )
                else -> {
                    oldState.copy(
                        contentContentState = ContentState.Error.UnknownError
                    )
                }
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
