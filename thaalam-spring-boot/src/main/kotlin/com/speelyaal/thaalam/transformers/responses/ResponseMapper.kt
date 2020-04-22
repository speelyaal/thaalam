package com.speelyaal.thaalam.transformers.responses

import com.speelyaal.thaalam.transformers.utils.ResourceTypeFactory

data class ResponseMapper(var thaalamType: ResourceTypeFactory.ResourceTypes
                                =ResourceTypeFactory.ResourceTypes.Region) {

    lateinit var listPath: String
    lateinit var mapping: HashMap<String, String>

}