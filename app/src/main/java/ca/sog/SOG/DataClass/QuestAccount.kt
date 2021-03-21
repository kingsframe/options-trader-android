package ca.sog.SOG.DataClass

data class QuestAccount (
        val type: String,
        val number: String,
        val status: String,
        val isPrimary: Boolean,
        val isBilling: Boolean,
        val clientAccountType: String,
)

// Sample json response for accounts from quest trade
//{
//    "accounts": [
//    {
//        "type": "Margin",
//        "number": "26598145",
//        "status": "Active",
//        "isPrimary": true,
//        "isBilling": true,
//        "clientAccountType": "Individual"
//    }
//    ]
//}