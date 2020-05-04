package com.speelyaal.thaalam.transformers.responses

import com.speelyaal.thaalam.transformers.utils.ResourceType
import com.speelyaal.thaalam.transformers.utils.ResourceTypeFactory

class ResponseMapper {

    lateinit var thaalamType: ResourceType
    lateinit var listPath: String
    lateinit var singleInstancePath: String
    lateinit var mapping: HashMap<String, String>

}