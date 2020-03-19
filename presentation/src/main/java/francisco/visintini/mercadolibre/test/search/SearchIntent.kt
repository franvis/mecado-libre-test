package francisco.visintini.mercadolibre.test.search

sealed class SearchIntent {
    data class Search(val query: String) : SearchIntent()
    data class TextChanged(val currentQuery: String) : SearchIntent()
    data class SearchFocus(val focused: Boolean) : SearchIntent()
    object ClearSearch : SearchIntent()
    object BackPressed : SearchIntent()
}
