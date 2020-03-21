package francisco.visintini.mercadolibre.test.product

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import dagger.android.support.AndroidSupportInjection
import francisco.visintini.mercadolibre.test.R
import francisco.visintini.mercadolibre.test.extensions.setVisible
import francisco.visintini.mercadolibre.test.product.ProductViewState.ContentViewState.Content
import francisco.visintini.mercadolibre.test.product.ProductViewState.ContentViewState.Loading
import francisco.visintini.mercadolibre.test.product.imagegallery.ProductImageGalleryItem
import francisco.visintini.mercadolibre.test.utils.ViewModelFactory
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import kotlinx.android.synthetic.main.fragment_product.*

class ProductFragment : Fragment(R.layout.fragment_product) {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory<ProductViewModel>

    private val disposable = CompositeDisposable()
    private val adapter = GroupAdapter<GroupieViewHolder>()
    private val productViewModel by viewModels<ProductViewModel> { viewModelFactory }
    private val args: ProductFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view_product_view_pager.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        productViewModel.viewState.observe(this, Observer(::render))
        productViewModel.start(args.productId)
    }

    private fun render(viewState: ProductViewState) {
        when (viewState.productContentViewState) {
            is Loading -> { // TODO put placeholders
            }
            is Content -> {
                with(viewState.productContentViewState) {
                    view_product_title.text = title
                    adapter.update(imageGalleryViewState.map { ProductImageGalleryItem(it) })
                    view_product_price.text = price
                    view_product_availability.text = availability
                    warranty?.let { view_product_warranty.text = it }
                        ?: view_product_warranty.setVisible(false)
                    condition?.let { view_product_condition.text = it }
                        ?: view_product_condition.setVisible(false)
                }
            }
        }
    }

    override fun onDetach() {
        disposable.clear()
        super.onDetach()
    }
}
