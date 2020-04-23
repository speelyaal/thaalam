package com.speelyaal.thaalam.transformers.utils

import com.speelyaal.thaalam.config.ConfigLoader
import com.speelyaal.thaalam.datamodel.Region
import com.speelyaal.thaalam.datamodel.ThaalamResource
import com.speelyaal.thaalam.datamodel.vm.VirtualMachine
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class ResourceTypeFactory {

    private val LOG: Logger = LogManager.getLogger(ResourceTypeFactory::class.java)

    enum class ResourceTypes {
        Region,
        VirtualMachine,
        None
    }

    companion object {

        private val LOG: Logger = LogManager.getLogger(ResourceTypeFactory::class.java)
        fun getInstance(resource: ResourceTypes): ThaalamResource? {

            when (resource) {
                ResourceTypes.Region -> return Region()
                ResourceTypes.VirtualMachine -> return VirtualMachine()
                else -> {
                    LOG.error("Resource not found  $resource")

                }
            }

            return null;

        }

    }
}