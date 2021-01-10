package ca.sog.SOG

data class Positions(
    val symbol: String,
    val symbolId: Int,
    val openQuantity: Int,
    val currentMarketValue: Int,
    val currentPrice: Float,
    val averageEntryPrice: Float,
    val closedPnl: Int,
    val openPnl: Int,
    val totalCost: Boolean,
    val isRealTime: String,
    val isUnderReorg: Boolean
)

/*
Sample json response for positions -
{
     "positions": [
           {
                "symbol": "THI.TO",
                "symbolId": 38738,
                "openQuantity": 100,
                "currentMarketValue": 6017,
                "currentPrice": 60.17,
                "averageEntryPrice": 60.23,
                "closedPnl": 0,
                "openPnl": -6,
                "totalCost": false,
                "isRealTime": "Individual",
                "isUnderReorg": false
           }
      ]
 }
*/