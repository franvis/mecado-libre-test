package francisco.visintini.mercadolibre.test.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<VS> : ViewModel() {
    protected val _viewState = MutableLiveData<VS>()
    val viewState: LiveData<VS>
        get() = _viewState
}