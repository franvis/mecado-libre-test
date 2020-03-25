package francisco.visintini.mercadolibre.test.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import francisco.visintini.mercadolibre.domain.entity.Result.Error
import francisco.visintini.mercadolibre.domain.entity.Result.Success
import francisco.visintini.mercadolibre.domain.error.ErrorEntity
import francisco.visintini.mercadolibre.domain.interactor.GetSearchResult
import francisco.visintini.mercadolibre.test.search.SearchNavigation.ToProduct
import francisco.visintini.mercadolibre.test.search.result.SearchResultItemVSMapper
import francisco.visintini.mercadolibre.test.search.result.fixtures.Constants
import francisco.visintini.mercadolibre.test.search.result.fixtures.SearchResultFixtures
import francisco.visintini.mercadolibre.test.search.result.fixtures.SummarizedProductFixtures
import francisco.visintini.mercadolibre.test.utils.MainCoroutineRule
import francisco.visintini.mercadolibre.test.utils.SingleLiveEvent
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import io.reactivex.internal.util.NotificationLite.getValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@Suppress("MaxLineLength")
class SearchViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @MockK(relaxed = true)
    private lateinit var getSearchResult: GetSearchResult

    @MockK(relaxed = true)
    private lateinit var searchResultItemVSMapper: SearchResultItemVSMapper

    @MockK(relaxed = true)
    private lateinit var savedStateHandle: SavedStateHandle

    private lateinit var searchViewModel: SearchViewModel

    @Before
    fun beforeTest() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        searchViewModel =
            SearchViewModel(savedStateHandle, getSearchResult, searchResultItemVSMapper)
    }

    @Test
    fun `handle intent - with search intent - updates view state with query and content to loading at beginning`() {
        // GIVEN
        coEvery { savedStateHandle.getLiveData<SearchViewState>(any()) } returns MutableLiveData(
            SearchViewState(contentState = ContentState.Initial())
        )
        searchViewModel.start()
        mainCoroutineRule.pauseDispatcher()

        // WHEN
        searchViewModel.handleIntent(SearchIntent.Search(Constants.MOCKED_SEARCH_QUERY))

        // THEN
        val viewState = getValue<MutableLiveData<SearchViewState>>(searchViewModel.viewState)?.value
        viewState?.contentState shouldBeInstanceOf ContentState.Loading::class
        viewState?.searchBarViewState?.query shouldEqual Constants.MOCKED_SEARCH_QUERY
        mainCoroutineRule.resumeDispatcher()
    }

    @Test
    fun `handle intent - with search intent and success response - calls the use case and the view state mapper returning a content view state`() {
        // GIVEN
        coEvery { savedStateHandle.getLiveData<SearchViewState>(any()) } returns MutableLiveData(
            SearchViewState(contentState = ContentState.Initial())
        )
        coEvery { getSearchResult.execute(any()) } returns Success(SearchResultFixtures.just())
        searchViewModel.start()

        // WHEN
        searchViewModel.handleIntent(SearchIntent.Search(Constants.MOCKED_SEARCH_QUERY))

        // THEN
        coVerify { getSearchResult.execute(Constants.MOCKED_SEARCH_QUERY) }
        verify { searchResultItemVSMapper.mapToViewState(SummarizedProductFixtures.just()) }
        val viewState = getValue<MutableLiveData<SearchViewState>>(searchViewModel.viewState)?.value
        viewState?.contentState shouldBeInstanceOf ContentState.Content::class
    }

    @Test
    fun `handle intent - with search intent and network error response - returns a view state with network retry error state`() {
        // GIVEN
        coEvery { savedStateHandle.getLiveData<SearchViewState>(any()) } returns MutableLiveData(
            SearchViewState(contentState = ContentState.Initial())
        )
        coEvery { getSearchResult.execute(any()) } returns Error(ErrorEntity.NetworkError)
        searchViewModel.start()

        // WHEN
        searchViewModel.handleIntent(SearchIntent.Search(Constants.MOCKED_SEARCH_QUERY))

        // THEN
        val viewState = getValue<MutableLiveData<SearchViewState>>(searchViewModel.viewState)?.value
        viewState?.contentState shouldBeInstanceOf ContentState.Error.NetworkErrorRetry::class
    }

    @Test
    fun `handle intent - with search intent and empty error response - returns a view state with empty state`() {
        // GIVEN
        coEvery { savedStateHandle.getLiveData<SearchViewState>(any()) } returns MutableLiveData(
            SearchViewState(contentState = ContentState.Initial())
        )
        coEvery { getSearchResult.execute(any()) } returns Error(ErrorEntity.NotFound)
        searchViewModel.start()

        // WHEN
        searchViewModel.handleIntent(SearchIntent.Search(Constants.MOCKED_SEARCH_QUERY))

        // THEN
        val viewState = getValue<MutableLiveData<SearchViewState>>(searchViewModel.viewState)?.value
        viewState?.contentState shouldBeInstanceOf ContentState.Empty::class
    }

    @Test
    fun `handle intent - with search intent and unknown error response - returns a view state with unknown error state`() {
        // GIVEN
        coEvery { savedStateHandle.getLiveData<SearchViewState>(any()) } returns MutableLiveData(
            SearchViewState(contentState = ContentState.Initial())
        )
        coEvery { getSearchResult.execute(any()) } returns Error(ErrorEntity.UnknownError)
        searchViewModel.start()

        // WHEN
        searchViewModel.handleIntent(SearchIntent.Search(Constants.MOCKED_SEARCH_QUERY))

        // THEN
        val viewState = getValue<MutableLiveData<SearchViewState>>(searchViewModel.viewState)?.value
        viewState?.contentState shouldBeInstanceOf ContentState.Error.UnknownError::class
    }

    @Test
    fun `handle intent - with search result tapped intent - navigates to product`() {
        // WHEN
        searchViewModel.handleIntent(SearchIntent.SearchResultTapped(Constants.MOCKED_PRODUCT_ID))

        // THEN
        val navigation = getValue<SingleLiveEvent<SearchNavigation>>(searchViewModel.navigator)
        navigation?.value shouldBeInstanceOf ToProduct::class
        (navigation?.value as? ToProduct)?.productId shouldEqual Constants.MOCKED_PRODUCT_ID
    }
}
