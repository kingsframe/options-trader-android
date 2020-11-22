package ca.sog.SOG

class Token(val url: String) { //initializer list
    //Use obj.variable to grab these
    lateinit var accessToken: String
    lateinit var refreshToken: String
    lateinit var tokenType: String
    var expiresIn : Int = 0;
    lateinit var apiServer: String

    init{ //Variables and secondary ctors are ran IN ORDER! Must put variable inits first!
        parseToken()
    } //ctor

    fun parseToken(){
        // https://www.example.com/#access_token=...&refresh_token=...&token_type= Bearer&expires_in=1800&api_server=https://api01.iq.questrade.com/
        val parts = url.split("#", "&") //Throw away first ele
        val partsList = mutableListOf<String>()
        for (part in parts){
            val tokenParts = part.split("=")
            if(tokenParts.size > 1) { //Don't add the "www.example.com"
                val token = tokenParts.get(1)
                partsList.add(token)
            }
        }

        accessToken = partsList[0]
        refreshToken = partsList[1]
        tokenType = partsList[2]
        expiresIn = partsList[3].toInt()
        apiServer = partsList[4]

    }

}