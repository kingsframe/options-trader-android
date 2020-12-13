package ca.sog.SOG

data class QuestAccount (
        val type: String,
        val number: String,
        val status: String,
        val isPrimary: Boolean,
        val isBilling: Boolean,
        val clientAccountType: String,
)