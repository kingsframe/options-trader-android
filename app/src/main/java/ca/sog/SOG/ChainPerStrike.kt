package ca.sog.SOG

data class ChainPerStrikePrice(
        val strikePrice: Double,
        val callSymbolId: Int,
        val putSymbolId: Int
)