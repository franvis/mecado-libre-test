package francisco.visintini.mercadolibre.test.di

import android.os.Bundle
import androidx.annotation.MainThread
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner

/**
 * Interface for constructing [ViewModel] that gets an instance of [SavedStateHandle]
 * as a parameter.
 *
 * Implement this interface passing all [ViewModel] dependencies to the implementation's
 * injectable constructor and return a new instance of the [ViewModel].
 *
 * Inject an instance of the implementation in a Fragment or Activity and pass it
 * to [withFactory] when using `by viewModels` like so:
 *
 * @see withFactory
 */
interface ViewModelFactory<out VM : ViewModel> {
    fun create(handle: SavedStateHandle): VM
}

class GenericSavedStateViewModelFactory<out VM : ViewModel>(
    private val viewModelFactory: ViewModelFactory<VM>,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        return viewModelFactory.create(handle) as T
    }
}

/**
 * Convenience function to use with `by viewModels` that creates an instance of
 * [GenericSavedStateViewModelFactory] that enables us to pass [SavedStateHandle]
 * to the [ViewModel]'s constructor.
 *
 * @param factory instance of [ViewModelFactory] that will be used to construct the [ViewModel]
 * @param defaultArgs Bundle with default values to populate the [SavedStateHandle]
 *
 * @see ViewModelFactory
 * @see GenericSavedStateViewModelFactory
 */
@MainThread
inline fun <reified VM : ViewModel> SavedStateRegistryOwner.withFactory(
    factory: ViewModelFactory<VM>,
    defaultArgs: Bundle? = null
) = GenericSavedStateViewModelFactory(factory, this, defaultArgs)
