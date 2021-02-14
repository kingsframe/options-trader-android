package ca.sog.SOG

data class ChainPerRoot(
        val optionRoot: String,
        val multiplier: Int,
        val chainPerStrikePrice: Array<ChainPerStrikePrice>
)