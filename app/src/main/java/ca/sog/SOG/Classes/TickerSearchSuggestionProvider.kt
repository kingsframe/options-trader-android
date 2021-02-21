package ca.sog.SOG.Classes

import android.content.SearchRecentSuggestionsProvider

class TickerSearchSuggestionProvider : SearchRecentSuggestionsProvider() {
    init {
        setupSuggestions(AUTHORITY, MODE )
    }
    companion object {
        const val AUTHORITY = "ca.sog.SOG.Classes.TickerSearchSuggestionProvider"
        const val MODE: Int = SearchRecentSuggestionsProvider.DATABASE_MODE_QUERIES
    }
}