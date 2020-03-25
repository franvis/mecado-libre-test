package francisco.visintini.mercadolibre.test.product

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import dagger.android.support.AndroidSupportInjection
import francisco.visintini.mercadolibre.test.R
import francisco.visintini.mercadolibre.test.di.withFactory
import francisco.visintini.mercadolibre.test.extensions.setVisible
import francisco.visintini.mercadolibre.test.messages.ErrorMessage
import francisco.visintini.mercadolibre.test.messages.MessageManager
import francisco.visintini.mercadolibre.test.product.ProductIntent.ImageGalleryPositionChanged
import francisco.visintini.mercadolibre.test.product.ProductNavigation.ToError
import francisco.visintini.mercadolibre.test.product.ProductViewState.ContentState.Content
import francisco.visintini.mercadolibre.test.product.ProductViewState.ContentState.Error
import francisco.visintini.mercadolibre.test.product.ProductViewState.ContentState.Loading
import francisco.visintini.mercadolibre.test.product.imagegallery.ProductImageGalleryItem
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import kotlin.contracts.ExperimentalContracts
import kotlinx.android.synthetic.main.fragment_product.*

@ExperimentalContracts
class ProductFragment : Fragment(R.layout.fragment_product) {
    @Inject
    lateinit var productViewModelFactory: ProductViewModel.Factory

    @Inject
    lateinit var messageManager: MessageManager

    private val disposable = CompositeDisposable()
    private val adapter = GroupAdapter<GroupieViewHolder>()
    private val productViewModel: ProductViewModel by viewModels {
        withFactory(productViewModelFactory)
    }
    private val args: ProductFragmentArgs by navArgs()
    private var currentViewState: ProductViewState? = null
    private lateinit var viewPagerCallback: ViewPager2.OnPageChangeCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view_product_view_pager.adapter = adapter
        subscribeToImageGalleryIntents { position ->
            productViewModel.handleIntent(
                ImageGalleryPositionChanged(
                    position
                )
            )
        }
    }

    override fun onStart() {
        super.onStart()
        productViewModel.viewState.observe(this, Observer(::render))
        productViewModel.start(args.productId)
    }

    @Suppress("NestedBlockDepth")
    private fun render(viewState: ProductViewState) {
        view_product_content_placeholder.setVisible(viewState.productContentState is Loading)
        view_product_content_group.setVisible(viewState.productContentState.isContent())
        if (currentViewState != viewState) {
            when (viewState.productContentState) {
                is Content -> {
                    with(viewState.productContentState) {
                        view_product_title.text = title
                        adapter.update(imageGalleryViewState.productImageGalleryItemViewStates.map {
                            ProductImageGalleryItem(it)
                        })
                        if (view_product_view_pager.currentItem !=
                            imageGalleryViewState.imagePosition
                        ) { // Prevents scrolling when fragment is recreated
                            view_product_view_pager.setCurrentItem(
                                imageGalleryViewState.imagePosition,
                                false
                            )
                        }
                        view_product_price.text = price
                        view_product_availability.text = availability
                        warranty?.let { view_product_warranty.text = it }
                            ?: view_product_warranty.setVisible(false)
                        condition?.let { view_product_condition.text = it }
                            ?: view_product_condition.setVisible(false)
                    }
                }
                is Error -> showError(viewState.productContentState)
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
                        productViewModel.handleIntent(ProductIntent.NetworkErrorRetryTapped)
                    }
                )
            }
            is Error.UnknownError -> navigate(
                ToError(getString(R.string.error_dialog_generic_message))
            )
        }
    }

    private fun navigate(navigation: ProductNavigation) {
        with(findNavController()) {
            // TODO Fast workaround to prevent current navigation destination is unknown exception.
            if (this.currentBackStackEntry?.destination?.id == R.id.product_fragment) {
                when (navigation) {
                    is ToError -> navigate(
                        ProductFragmentDirections.actionSearchFragmentToErrorDialogFragment(
                            navigation.message
                        )
                    )
                }
            }
        }
    }

    private fun subscribeToImageGalleryIntents(onPageSelected: (position: Int) -> Unit) {
        viewPagerCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                onPageSelected.invoke(position)
            }
        }
        view_product_view_pager.registerOnPageChangeCallback(viewPagerCallback)
    }

    override fun onDestroyView() {
        disposable.clear()
        view_product_view_pager.unregisterOnPageChangeCallback(viewPagerCallback)
        super.onDestroyView()
    }
}
