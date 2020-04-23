package com.speelyaal.thaalam.transformers.responses

import com.speelyaal.thaalam.transformers.utils.ResourceTypeFactory

class ResponseMapper {

    lateinit var thaalamType: ResourceTypeFactory.ResourceTypes
    lateinit var listPath: String
    lateinit var singleInstancePath: String
    lateinit var mapping: HashMap<String, String>

}