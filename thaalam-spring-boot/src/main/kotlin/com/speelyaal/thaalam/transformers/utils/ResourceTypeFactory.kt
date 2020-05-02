package com.speelyaal.thaalam.transformers.utils

import com.speelyaal.thaalam.datamodel.Region
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

    enum class ResourceTypes {
        Region,
        VirtualMachine,
        VirtualMachineType,
        OperatingSystemImage,
        SSHKey,
        Network,
        FloatingIP,
        None
    }

    companion object {

        private val LOG: Logger = LogManager.getLogger(ResourceTypeFactory::class.java)
        fun getInstance(resource: ResourceTypes): ThaalamResource? {

            when (resource) {
                ResourceTypes.Region -> return Region()
                ResourceTypes.VirtualMachine -> return VirtualMachine()
                ResourceTypes.VirtualMachineType -> return VirtualMachineType()
                ResourceTypes.OperatingSystemImage -> return OperatingSystemImage()
                ResourceTypes.SSHKey -> return SSHKey()
                ResourceTypes.Network -> return Network()
                ResourceTypes.FloatingIP -> return FloatingIP()
                else -> {
                    LOG.error("Resource not found  $resource")
                }
            }

            return null;

        }



    }
}