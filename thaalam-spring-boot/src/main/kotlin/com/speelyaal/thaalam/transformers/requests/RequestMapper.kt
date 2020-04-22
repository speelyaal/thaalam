package com.speelyaal.thaalam.transformers.requests

import com.speelyaal.thaalam.transformers.utils.ResourceTypeFactory
import org.springframework.http.HttpMethod



data class RequestMapper(var thaalamType: ResourceTypeFactory.ResourceTypes =
                                 ResourceTypeFactory.ResourceTypes.Region) {

    lateinit var getAll: Method
    lateinit var getByReference: Method



    class Method {
        lateinit var method: HttpMethod
        lateinit var path: String
    }


}

