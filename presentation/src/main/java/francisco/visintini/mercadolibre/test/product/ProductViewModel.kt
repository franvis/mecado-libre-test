package francisco.visintini.mercadolibre.test.product

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import francisco.visintini.mercadolibre.domain.entity.ErrorEntity
import francisco.visintini.mercadolibre.domain.entity.Result.Error
import francisco.visintini.mercadolibre.domain.entity.Result.Success
import francisco.visintini.mercadolibre.domain.interactor.GetProduct
import francisco.visintini.mercadolibre.test.di.ViewModelFactory
import francisco.visintini.mercadolibre.test.product.ProductIntent.ImageGalleryPositionChanged
import francisco.visintini.mercadolibre.test.product.ProductViewState.ContentState.Initial
import francisco.visintini.mercadolibre.test.product.ProductViewState.ContentState.Loading
import francisco.visintini.mercadolibre.test.utils.BaseSavedStateViewModel
import javax.inject.Inject
import kotlin.contracts.ExperimentalContracts
import kotlinx.coroutines.launch

@ExperimentalContracts
class ProductViewModel(
    override val handle: SavedStateHandle,
    private val getProduct: GetProduct,
    private val productContentViewStateMapper: ProductContentViewStateMapper
) : BaseSavedStateViewModel<ProductViewState>(handle) {

    fun start(productId: String) {
        if (getSavedState().value != null) {
            _viewState.value = getSavedState().value
        } else {
            _viewState.value = ProductViewState(productId, Initial)
            handleLoadProduct(productId)
        }
    }

    fun handleIntent(intent: ProductIntent) {
        when (intent) {
            is ImageGalleryPositionChanged -> handleGalleryPositionChanged(intent)
        }
    }

    private fun handleGalleryPositionChanged(intent: ImageGalleryPositionChanged) {
        updateViewState { oldState ->
            oldState.copy(
                productContentState = if (oldState.productContentState.isContent() &&
                    oldState.productContentState.imageGalleryViewState.imagePosition != intent.newPosition
                ) {
                    oldState.productContentState.copy(
                        imageGalleryViewState = oldState.productContentState.imageGalleryViewState.copy(
                            imagePosition = intent.newPosition
                        )
                    )
                } else oldState.productContentState
            )
        }
    }

    private fun handleLoadProduct(productId: String) {
        updateViewState { oldState ->
            oldState.copy(productContentState = Loading)
        }
        viewModelScope.launch {
            try {
                when (val result = getProduct.execute(productId)) {
                    is Success -> {
                        updateViewState { oldState ->
                            oldState.copy(
                                productContentState = productContentViewStateMapper.transform(
                                    result.result
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
        private val getProduct: GetProduct,
        private val productContentViewStateMapper: ProductContentViewStateMapper
    ) : ViewModelFactory<ProductViewModel> {
        override fun create(handle: SavedStateHandle): ProductViewModel {
            return ProductViewModel(handle, getProduct, productContentViewStateMapper)
        }
    }
}
