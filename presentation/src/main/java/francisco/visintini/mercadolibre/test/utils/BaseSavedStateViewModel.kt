package francisco.visintini.mercadolibre.test.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

abstract class BaseSavedStateViewModel<VS>(open val handle: SavedStateHandle) : ViewModel() {
    protected val _viewState = MutableLiveData<VS>()
    val viewState: LiveData<VS>
        get() = _viewState

    protected fun updateViewState(reducer: (VS) -> VS) {
        _viewState.value?.run { _viewState.value = reducer(this) }
        saveViewState()
    }

    private fun saveViewState() {
        _viewState.value?.run { handle[KEY_STATE] = this }
    }

    protected fun getSavedState() = handle.getLiveData<VS>(KEY_STATE)

    companion object {
        const val KEY_STATE = "KEY_STATE"
    }
}
