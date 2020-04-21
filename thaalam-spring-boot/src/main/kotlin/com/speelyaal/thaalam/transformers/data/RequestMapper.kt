package com.speelyaal.thaalam.transformers.data

import org.springframework.http.HttpMethod


data class RequestMapper(var type: String ="") {

    lateinit var methods: HashMap<HttpMethod, Properties>



    class Properties {
        lateinit var path: String
    }


}

