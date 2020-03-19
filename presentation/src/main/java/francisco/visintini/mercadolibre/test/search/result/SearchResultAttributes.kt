package francisco.visintini.mercadolibre.test.search.result

import android.annotation.SuppressLint
import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.parcel.Parcelize

class SearchResultAttributes @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    private val adapter = GroupAdapter<GroupieViewHolder>()

    init {
        layoutManager = LinearLayoutManager(context)
        isNestedScrollingEnabled = false
        overScrollMode = View.OVER_SCROLL_NEVER
        setAdapter(adapter)
    }

    fun render(viewState: ViewState) {
        isVisible = viewState.attributes.isNotEmpty()
        adapter.update(viewState.attributes.map(::SearchResultAttributeItem))
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(e: MotionEvent?): Boolean {
        return false
    }

    @Parcelize
    data class ViewState(val attributes: List<SearchResultAttributeItem.ViewState>) : Parcelable
}
