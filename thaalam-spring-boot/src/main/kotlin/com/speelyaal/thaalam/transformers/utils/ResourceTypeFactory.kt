package com.speelyaal.thaalam.transformers.utils

import com.speelyaal.thaalam.datamodel.Location
import com.speelyaal.thaalam.datamodel.ResourceType
import com.speelyaal.thaalam.datamodel.ThaalamResource
import com.speelyaal.thaalam.datamodel.network.FloatingIP
import com.speelyaal.thaalam.datamodel.network.Network
import com.speelyaal.thaalam.datamodel.vm.OperatingSystemImage
import com.speelyaal.thaalam.datamodel.vm.SSHKey
import com.speelyaal.thaalam.datamodel.vm.VirtualMachine
import com.speelyaal.thaalam.datamodel.vm.VirtualMachineType
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class ResourceTypeFactory {

    private val LOG: Logger = LogManager.getLogger(ResourceTypeFactory::class.java)



    companion object {

        private val LOG: Logger = LogManager.getLogger(ResourceTypeFactory::class.java)
        fun getInstance(resource: ResourceType): ThaalamResource? {

            when (resource) {
                ResourceType.Location -> return Location()
                ResourceType.VirtualMachine -> return VirtualMachine()
                ResourceType.VirtualMachineType -> return VirtualMachineType()
                ResourceType.OperatingSystemImage -> return OperatingSystemImage()
                ResourceType.SSHKey -> return SSHKey()
                ResourceType.Network -> return Network()
                ResourceType.FloatingIP -> return FloatingIP()
                else -> {
                    LOG.error("Resource not found  $resource")
                }
            }

            return null;

        }



    }
}