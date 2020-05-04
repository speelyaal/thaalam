package com.speelyaal.thaalam.transformers.requests

import com.speelyaal.thaalam.datamodel.ResourceType
import org.springframework.http.HttpMethod


class RequestMapper {

    lateinit var thaalamType: ResourceType
    lateinit var getAll: Method
    lateinit var getByReference: Method
    lateinit var create: Method
    lateinit var update: Method
    lateinit var delete: Method

    class Method {
        lateinit var method: HttpMethod
        lateinit var path: String
        lateinit var body: HashMap<String, String>
    }


}

