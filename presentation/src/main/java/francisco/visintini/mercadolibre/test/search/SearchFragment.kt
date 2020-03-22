package francisco.visintini.mercadolibre.test.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import dagger.android.support.AndroidSupportInjection
import francisco.visintini.mercadolibre.test.R
import francisco.visintini.mercadolibre.test.di.withFactory
import francisco.visintini.mercadolibre.test.extensions.setVisible
import francisco.visintini.mercadolibre.test.messages.ErrorMessage
import francisco.visintini.mercadolibre.test.messages.MessageManager
import francisco.visintini.mercadolibre.test.search.ContentState.Content
import francisco.visintini.mercadolibre.test.search.ContentState.Empty
import francisco.visintini.mercadolibre.test.search.ContentState.Error
import francisco.visintini.mercadolibre.test.search.ContentState.Initial
import francisco.visintini.mercadolibre.test.search.ContentState.Loading
import francisco.visintini.mercadolibre.test.search.SearchIntent.ClearSearch
import francisco.visintini.mercadolibre.test.search.SearchIntent.Search
import francisco.visintini.mercadolibre.test.search.SearchIntent.SearchBarBackPressed
import francisco.visintini.mercadolibre.test.search.SearchIntent.SearchFocus
import francisco.visintini.mercadolibre.test.search.SearchIntent.TextChanged
import francisco.visintini.mercadolibre.test.search.SearchNavigation.ToError
import francisco.visintini.mercadolibre.test.search.SearchNavigation.ToProduct
import francisco.visintini.mercadolibre.test.search.bar.SearchBar.SearchBarIntent
import francisco.visintini.mercadolibre.test.search.result.SearchResultItem
import francisco.visintini.mercadolibre.test.search.result.SearchResultItemPlaceholder
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment(R.layout.fragment_search) {

    @Inject
    lateinit var searchViewModelFactory: SearchViewModel.Factory
    @Inject
    lateinit var messageManager: MessageManager

    private val disposable = CompositeDisposable()
    private val adapter = GroupAdapter<GroupieViewHolder>()
    private val searchViewModel: SearchViewModel by viewModels { withFactory(searchViewModelFactory) }
    private var currentViewState: SearchViewState? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view_search_list.layoutManager = LinearLayoutManager(context)
        view_search_list.addItemDecoration(DividerItemDecoration(context, VERTICAL))
        view_search_list.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        searchViewModel.viewState.observe(this, Observer { this.render(it) })
        searchViewModel.navigator.observe(this, Observer { this.navigate(it) })
        subscribeToSearchBarIntents()
        subscribeToSearchResultItemIntents()
        searchViewModel.start()
    }

    private fun render(viewState: SearchViewState) {
        view_search_bar.render(viewState.searchBarViewState)
        val content = viewState.contentState
        view_search_empty_result.setVisible(content is Empty)
        view_search_list.setVisible(content is Content || content is Loading)
        if (currentViewState?.contentState != content) { // Avoid unnecessary re-rendering
            when (content) {
                is Initial -> adapter.clear() // TODO Add recently searched here
                is Loading -> adapter.update((1..4).map { SearchResultItemPlaceholder })
                is Content -> adapter.update(content.searchResults.map { SearchResultItem(it) })
                is Empty -> {
                    adapter.clear()
                    view_search_empty_result.text = getString(
                        R.string.search_empty_results_text,
                        viewState.searchBarViewState.query
                    )
                }
                is Error -> {
                    adapter.clear()
                    showError(content)
                }
            }
            currentViewState = viewState
        }
    }

    private fun showError(error: Error) {
        when (error) {
            is Error.NetworkErrorRetry -> view?.let {
                messageManager.showError(
                    it,
                    ErrorMessage.NetworkErrorRetry {
                        searchViewModel.handleIntent(SearchIntent.NetworkErrorRetryTapped)
                    }
                )
            }
            is Error.UnknownError -> navigate(
                ToError(getString(R.string.error_dialog_generic_message))
            )
        }
    }

    private fun navigate(navigation: SearchNavigation) {
        with(findNavController()) {
            // TODO Fast workaround to prevent current navigation destination is unknown exception.
            if (this.currentBackStackEntry?.destination?.id == R.id.search_fragment) {
                when (navigation) {
                    is ToProduct -> navigate(
                        SearchFragmentDirections.actionSearchFragmentToProductFragment(
                            navigation.productId
                        )
                    )
                    is ToError -> navigate(
                        SearchFragmentDirections.actionSearchFragmentToErrorDialogFragment(
                            navigation.message
                        )
                    )
                }
            }
        }
    }

    override fun onDestroyView() {
        view_search_list.adapter = null
        disposable.clear()
        super.onDestroyView()
    }

    private fun subscribeToSearchResultItemIntents() {
        adapter.setOnItemClickListener { item, _ ->
            when (item) {
                is SearchResultItem -> searchViewModel.handleIntent(
                    SearchIntent.SearchResultTapped(item.viewState.productId)
                )
            }
        }
    }

    private fun subscribeToSearchBarIntents() {
        view_search_bar.getIntents()
            .subscribe {
                searchViewModel.handleIntent(
                    when (it) {
                        is SearchBarIntent.Search -> Search(it.query)
                        is SearchBarIntent.TextChanged -> TextChanged(it.currentQuery)
                        is SearchBarIntent.SearchFocus -> SearchFocus(it.focused)
                        is SearchBarIntent.ClearSearch -> ClearSearch
                        is SearchBarIntent.BackPressed -> SearchBarBackPressed
                    }
                )
            }
            .addTo(disposable)
    }
}
