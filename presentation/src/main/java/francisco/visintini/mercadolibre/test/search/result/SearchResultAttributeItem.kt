package francisco.visintini.mercadolibre.test.search.result

import android.content.Context
import android.os.Build
import android.os.Parcelable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.BulletSpan
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import francisco.visintini.mercadolibre.test.R
import francisco.visintini.mercadolibre.test.extensions.convertDpToPixels
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.item_search_result_attribute.*

data class SearchResultAttributeItem(val viewState: ViewState) : Item() {
    override fun getLayout(): Int = R.layout.item_search_result_attribute

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        val context = viewHolder.itemView.context
        val description = context.getString(
            R.string.search_result_attribute_description,
            viewState.name,
            viewState.description
        )

        viewHolder.view_attribute_description.text =
            getDescriptionWithBullet(description, context)
    }

    private fun getDescriptionWithBullet(description: String, context: Context): Spanned {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val spannableDescription = SpannableString(description)
            spannableDescription.setSpan(
                BulletSpan(
                    context.convertDpToPixels(BULLET_MARGIN_DP),
                    ContextCompat.getColor(context, R.color.black),
                    BULLET_RADIUS_DP
                ),
                0,
                description.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            spannableDescription
        } else {
            HtmlCompat.fromHtml(
                DESCRIPTION_WITH_BULLET_HTML.format(description),
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )
        }
    }

    @Parcelize
    data class ViewState(val name: String, val description: String) : Parcelable

    companion object {
        private const val BULLET_MARGIN_DP = 8
        private const val BULLET_RADIUS_DP = 6
        private const val DESCRIPTION_WITH_BULLET_HTML = "&#8226;&#160;%s"
    }
}
