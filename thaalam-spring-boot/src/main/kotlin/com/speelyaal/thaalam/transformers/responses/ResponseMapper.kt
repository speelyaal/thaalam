package com.speelyaal.thaalam.transformers.responses

import com.speelyaal.thaalam.datamodel.ResourceType

class ResponseMapper {

    lateinit var thaalamType: ResourceType
    lateinit var listPath: String
    lateinit var singleInstancePath: String
    lateinit var mapping: HashMap<String, String>

}