package francisco.visintini.mercadolibre.test.product

import android.util.Log
import androidx.lifecycle.viewModelScope
import francisco.visintini.mercadolibre.domain.entity.ErrorEntity
import francisco.visintini.mercadolibre.domain.entity.Result.Error
import francisco.visintini.mercadolibre.domain.entity.Result.Success
import francisco.visintini.mercadolibre.domain.interactor.GetProduct
import francisco.visintini.mercadolibre.test.product.ProductViewState.ContentViewState.Initial
import francisco.visintini.mercadolibre.test.product.ProductViewState.ContentViewState.Loading
import francisco.visintini.mercadolibre.test.utils.BaseViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

class ProductViewModel @Inject constructor(
    private val getProduct: GetProduct,
    private val productContentViewStateMapper: ProductContentViewStateMapper
) :
    BaseViewModel<ProductViewState>() {

    fun start(productId: String) {
        _viewState.value = ProductViewState(productId, Initial)
        handleLoadProduct(productId)
    }

    private fun handleLoadProduct(productId: String) {
        updateViewState { oldState ->
            oldState.copy(productContentViewState = Loading)
        }
        viewModelScope.launch {
            try {
                when (val result = getProduct.execute(productId)) {
                    is Success -> {
                        updateViewState { oldState ->
                            oldState.copy(
                                productContentViewState = productContentViewStateMapper.transform(
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
}
