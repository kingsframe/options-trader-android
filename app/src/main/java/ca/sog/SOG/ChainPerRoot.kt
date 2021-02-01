package ca.sog.SOG

data class ChainPerRoot(
        val root: String,
        val multiplier: Int,
        val chainPerStrikePrice: ChainPerStrikePrice
)
