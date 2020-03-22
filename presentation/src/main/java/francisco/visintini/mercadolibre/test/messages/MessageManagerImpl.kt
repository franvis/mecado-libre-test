package francisco.visintini.mercadolibre.test.messages

import android.view.View
import com.google.android.material.snackbar.Snackbar
import francisco.visintini.mercadolibre.test.R
import francisco.visintini.mercadolibre.test.messages.ErrorMessage.NetworkErrorRetry
import francisco.visintini.mercadolibre.test.utils.ResourceProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessageManagerImpl @Inject constructor(private val resourceProvider: ResourceProvider) :
    MessageManager {
    private lateinit var snackbar: Snackbar
    override fun showError(view: View, errorMessage: ErrorMessage) {
        when (errorMessage) {
            is NetworkErrorRetry -> {
                // if(::snackbar.isInitialized) {
                //     snackbar.dismiss()
                // }
                snackbar = Snackbar.make(
                    view,
                    resourceProvider.getString(R.string.error_message_network),
                    Snackbar.LENGTH_LONG
                )
                snackbar.setAction(R.string.error_message_network_retry) {
                    errorMessage.onRetryTapped.invoke()
                }
                snackbar.show()
            }
        }
    }
}
