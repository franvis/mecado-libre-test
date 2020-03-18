package francisco.visintini.mercadolibre.test.search

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel

interface ViewModelAccessor {
    var viewModel: ViewModel
    val activity: FragmentActivity
}
