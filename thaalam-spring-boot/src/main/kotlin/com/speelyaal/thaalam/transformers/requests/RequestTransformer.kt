package com.speelyaal.thaalam.transformers.requests

import com.speelyaal.thaalam.config.ConfigLoader
import com.speelyaal.thaalam.datamodel.CloudProviderName

import com.speelyaal.thaalam.datamodel.ResourceName
import com.speelyaal.thaalam.datamodel.ThaalamResource
import com.speelyaal.thaalam.datamodel.vm.VirtualMachine
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
                                resourceToCreate: T): Any {

        val temp: VirtualMachine = resourceToCreate as VirtualMachine

        LOG.debug("Thaalam resource Type is  " + resourceToCreate!!::class.java)
        LOG.debug("Converted Type is  " + temp::class.java)
        LOG.debug("After conversion reading a field --> " + temp.osImageIdentifier)


        return Any()
    }







}

