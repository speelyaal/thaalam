package com.speelyaal.thaalam.transformers.utils

import com.speelyaal.thaalam.config.ConfigLoader
import com.speelyaal.thaalam.datamodel.CloudProviderList
import com.speelyaal.thaalam.transformers.data.RequestTransformer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class RestHelper {

    @Autowired
    private lateinit var config: ConfigLoader

    @Autowired
    private lateinit var requestTransformer: RequestTransformer

    @Autowired
    lateinit var responseTransformer: RequestTransformer

    fun getResource(cloudProvider: CloudProviderList, resource: String, apiToken: String): Any {




       return ArrayList<String>()
    }

}