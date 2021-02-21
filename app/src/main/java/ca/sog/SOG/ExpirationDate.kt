package ca.sog.SOG

data class ExpirationDate(
        val expiryDate: String,
        val description: String,
        val listingExchange: String,
        val optionExerciseType: String,
        val chainPerRoot: Array<ChainPerRoot>
)

/*
Sample JSON response

{
    "options": [
	{
		"expiryDate": "2015-01-17T00:00:00.000000-05:00",
		"description":   "BANK OF MONTREAL",
		"listingExchange":   "MX",
		"optionExerciseType":   "American",
		"chainPerRoot": [
		    {
			"root":   "BMO",
			"chainPerStrikePrice": [
				{
					"strikePrice":   60,
					"callSymbolId":  6101993,
					"putSymbolId":   6102009
				},
				{
					"strikePrice":   62,
					"callSymbolId":   6101994,
					"putSymbolId":   6102010
				},
				{
					"strikePrice":   64,
					"callSymbolId":   6101995,
					"putSymbolId":   6102011
				},
	            			...
				],
		       	"multiplier":   100
		      }
	       ]
        }
     ]
}
*/