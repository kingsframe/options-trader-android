package ca.sog.SOG.DataClass

data class ChainPerRoot(
        val optionRoot: String,
        val multiplier: Int,
        val chainPerStrikePrice: Array<ChainPerStrikePrice>
)