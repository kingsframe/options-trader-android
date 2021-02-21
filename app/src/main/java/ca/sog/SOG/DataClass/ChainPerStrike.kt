package ca.sog.SOG.DataClass

data class ChainPerStrikePrice(
        val strikePrice: Double,
        val callSymbolId: Int,
        val putSymbolId: Int
)