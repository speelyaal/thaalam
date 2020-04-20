package com.speelyaal.thaalam.config

class ProviderConfigurations {
    lateinit var apiVersion: String
    lateinit var apiUrl: String
    lateinit var authorizationType: AuthorizationType
    lateinit var resources: Array<String>


    enum class AuthorizationType {
        bearerToken,
        clientIdSecret

    }


}

