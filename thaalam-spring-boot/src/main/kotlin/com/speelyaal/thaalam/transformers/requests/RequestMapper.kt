package com.speelyaal.thaalam.transformers.requests

import com.speelyaal.thaalam.transformers.utils.ResourceTypeFactory
import org.springframework.http.HttpMethod
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater


class RequestMapper {

    lateinit var thaalamType: ResourceTypeFactory.ResourceTypes
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

