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
import francisco.visintini.mercadolibre.test.search.SearchIntent.BackPressed
import francisco.visintini.mercadolibre.test.search.SearchIntent.ClearSearch
import francisco.visintini.mercadolibre.test.search.SearchIntent.Search
import francisco.visintini.mercadolibre.test.search.SearchIntent.SearchFocus
import francisco.visintini.mercadolibre.test.search.SearchIntent.TextChanged
import francisco.visintini.mercadolibre.test.search.bar.SearchBar.SearchBarIntent
import francisco.visintini.mercadolibre.test.search.result.SearchContentViewState
import francisco.visintini.mercadolibre.test.search.result.SearchResultItem
import francisco.visintini.mercadolibre.test.search.result.SearchResultItemPlaceholder
import francisco.visintini.mercadolibre.test.search.result.SearchViewState
import francisco.visintini.mercadolibre.test.utils.ViewModelFactory
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment(R.layout.fragment_search) {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory<SearchViewModel>

    private val disposable = CompositeDisposable()
    private val adapter = GroupAdapter<GroupieViewHolder>()
    private val searchViewModel by viewModels<SearchViewModel> { viewModelFactory }

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
    }

    private fun render(viewState: SearchViewState) {
        view_search_bar.render(viewState.searchBarViewState)
        when (viewState.searchContentViewState) {
            is SearchContentViewState.Initial -> adapter.clear()
            is SearchContentViewState.Loading -> {
                adapter.update((1..4).map { SearchResultItemPlaceholder })
            }
            is SearchContentViewState.Content -> adapter.update(
                viewState.searchContentViewState.searchResults.map {
                    SearchResultItem(it)
                }
            )
        }
    }

    private fun navigate(navigation: SearchNavigation) {
        with(findNavController()) {
            when (navigation) {
                is SearchNavigation.ToProduct -> navigate(
                    SearchFragmentDirections.actionSearchFragmentToProductFragment(
                        navigation.productId
                    )
                )
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
                        is SearchBarIntent.BackPressed -> BackPressed
                    }
                )
            }
            .addTo(disposable)
    }
}
