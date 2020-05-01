package com.speelyaal.thaalam.transformers.requests

import com.speelyaal.thaalam.config.ConfigLoader
import com.speelyaal.thaalam.datamodel.CloudProviderName

import com.speelyaal.thaalam.datamodel.ResourceName
import com.speelyaal.thaalam.datamodel.ThaalamResource
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component


@Component
class RequestTransformer {

    private val LOG: Logger = LogManager.getLogger(RequestTransformer::class.java)

    @Autowired
    lateinit var config:  ConfigLoader

    fun <T> transformResourceToPost(cloudProvider: CloudProviderName,
                                    resourceName: ResourceName,
                                    resourceToCreate: T,
                                    mapping: HashMap<String, String>): Map<String, Any?> {
        if(resourceToCreate != null) {
            LOG.debug("Thaalam resource Type is  ${resourceToCreate}")
        }

        var resultMap = HashMap<String, Any?>()
        var resourceObject = resourceToCreate as ThaalamResource
        mapping.forEach {property ->

            var tempVal: Any? = resourceObject.getPropertyValue(property.value)


            resultMap[property.key] = tempVal

        }


        return resultMap
    }







}

