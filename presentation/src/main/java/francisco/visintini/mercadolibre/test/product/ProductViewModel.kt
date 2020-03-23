package francisco.visintini.mercadolibre.test.product

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import francisco.visintini.mercadolibre.domain.entity.Result.Error
import francisco.visintini.mercadolibre.domain.entity.Result.Success
import francisco.visintini.mercadolibre.domain.error.ErrorEntity
import francisco.visintini.mercadolibre.domain.interactor.GetProduct
import francisco.visintini.mercadolibre.test.di.ViewModelFactory
import francisco.visintini.mercadolibre.test.product.ProductIntent.ImageGalleryPositionChanged
import francisco.visintini.mercadolibre.test.product.ProductViewState.ContentState.Error.NetworkErrorRetry
import francisco.visintini.mercadolibre.test.product.ProductViewState.ContentState.Error.UnknownError
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
            is ProductIntent.NetworkErrorRetryTapped -> _viewState.value?.productId?.let {
                handleLoadProduct(it)
            }
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
                        updateViewStateForError(result.error)
                    }
                }
            } catch (exception: Exception) {
                updateViewStateForError()
                // TODO Log or track presentation exception here
            }
        }
    }

    private fun updateViewStateForError(error: ErrorEntity = ErrorEntity.UnknownError) {
        updateViewState { oldState ->
            when (error) {
                is ErrorEntity.NetworkError, ErrorEntity.ServiceUnavailable -> {
                    oldState.copy(
                        productContentState = NetworkErrorRetry
                    )
                }
                else -> {
                    oldState.copy(
                        productContentState = UnknownError
                    )
                }
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
