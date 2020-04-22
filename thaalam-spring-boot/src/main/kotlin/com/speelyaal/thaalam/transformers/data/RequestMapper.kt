package com.speelyaal.thaalam.transformers.data

import org.springframework.http.HttpMethod


data class RequestMapper(var type: String ="") {

    lateinit var getAll: Method
    lateinit var getByReference: Method



    class Method {
        lateinit var method: HttpMethod
        lateinit var path: String
    }


}

