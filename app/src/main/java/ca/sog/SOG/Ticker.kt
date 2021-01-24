package ca.sog.SOG

data class Ticker (
        val symbol: String,
        val symbolId: Int,
        val description: String,
        val securityType: String,
        val listingExchange: String,
        val isTradable: Boolean,
        val isQuotable: Boolean,
        val currency: String,
)

// Sample json response for accounts from quest trade
//{
//    "symbol": [
//    {
//        "symbol":  "BMO",
//        "symbolId":  9292,
//        "description":  "BANK OF MONTREAL",
//        "securityType": "Stock",
//        "listingExchange": "NYSE",
//        "isTradable":  true,
//        "isQuotable":  true,
//        "currency":  "USD"
//    },
//    {
//        "symbol":  "BMO.PRJ.TO",
//        "symbolId":  9300,
//        "description":  "BANK OF MONTREAL CL B SR 13",
//        "securityType":  "Stock",
//        "listingExchange":  "TSX",
//        "isTradable":  true,
//        "isQuotable":  true,
//        "currency":  "CAD"
//    }
//    ]
//}
