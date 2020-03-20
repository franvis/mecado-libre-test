package francisco.visintini.mercadolibre.test.search.result

import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import francisco.visintini.mercadolibre.test.R
import francisco.visintini.mercadolibre.test.extensions.load
import kotlinx.android.synthetic.main.item_search_result.*

data class SearchResultItem(val viewState: ViewState) : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        with(viewHolder) {
            view_search_result_image.load(viewState.imageUrl)
            view_search_result_title.text = viewState.title
            view_search_result_attributes.render(viewState.searchResultAttributesViewState)
            view_search_result_price.text = viewState.price
        }
    }

    override fun getLayout(): Int = R.layout.item_search_result

    data class ViewState(
        val imageUrl: String,
        val title: String,
        val productId: String,
        val searchResultAttributesViewState: SearchResultAttributes.ViewState,
        val price: String
    )

    override fun isSameAs(other: com.xwray.groupie.Item<*>): Boolean {
        return other is SearchResultItem && other.viewState == this.viewState
    }
}

object SearchResultItemPlaceholder : Item() {

    override fun getLayout(): Int = R.layout.item_search_result_placeholder

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        // NO OP
    }
}
