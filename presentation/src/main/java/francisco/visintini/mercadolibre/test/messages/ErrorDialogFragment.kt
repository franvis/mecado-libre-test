package francisco.visintini.mercadolibre.test.messages

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import francisco.visintini.mercadolibre.test.R

class ErrorDialogFragment : DialogFragment() {

    private val args: ErrorDialogFragmentArgs by navArgs()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        context?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(R.string.error_dialog_title)
            builder.setMessage(args.errorMessage)
            builder.setPositiveButton(R.string.error_dialog_ok, null)
            return builder.create()
        }
        return super.onCreateDialog(savedInstanceState)
    }
}
