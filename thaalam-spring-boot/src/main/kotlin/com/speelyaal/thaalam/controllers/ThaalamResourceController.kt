package com.speelyaal.thaalam.controllers

import com.speelyaal.thaalam.datamodel.CloudProviderName
import com.speelyaal.thaalam.datamodel.ResourceType
import com.speelyaal.thaalam.transformers.utils.RestHelper
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("resources")
class ThaalamResourceController(var restHelper: RestHelper){


    private val LOG: Logger = LogManager.getLogger(ThaalamResourceController::class.java)




    @GetMapping("{resource}")
    fun getAllResources(@PathVariable("resource") resourceName: String,
                        @RequestHeader("X-Request-ID") requestId: String,
                        @RequestHeader("X-Cloud-Provider") cloudProvider: CloudProviderName,
                        @RequestHeader(value = "X-API-Credentials", required = false) apiCredentials: String="" ): Any {


        LOG.debug("Get resources.... ${resourceName} - ${cloudProvider} -- ${apiCredentials} -- ${requestId}")



        return restHelper.getResources(cloudProvider,this.getResourceType(resourceName),apiCredentials)

    }

    @GetMapping("{resource}/{reference}")
    fun getResourceByReference(@PathVariable("resource") resourceName: String,
                               @PathVariable("reference") vendorReference: String,
                               @RequestHeader("X-Request-ID") requestId: String,
                               @RequestHeader("X-Cloud-Provider") cloudProvider: CloudProviderName,
                               @RequestHeader(value = "X-API-Credentials", required = false) apiCredentials: String=""
                               ): Any {
        return restHelper.getResourceByReference(cloudProvider,this.getResourceType(resourceName),apiCredentials, vendorReference)

    }


    @PostMapping("{resource}")
    fun createResource(@PathVariable("resource") resourceName: String,
                       @RequestHeader("X-Request-ID") requestId: String,
                       @RequestHeader("X-Cloud-Provider") cloudProvider: CloudProviderName,
                       @RequestHeader(value = "X-API-Credentials", required = false) apiCredentials: String="",
                       @RequestBody resourceToCreate: Any ): Any{



        return restHelper.createResource(cloudProvider,this.getResourceType(resourceName),apiCredentials, resourceToCreate)
    }

    @PutMapping("{resource}/{reference}")
    fun updateResource(@PathVariable("resource") resourceName: String,
                       @PathVariable("reference") vendorReference: String,
                       @RequestHeader("X-Request-ID") requestId: String,
                       @RequestHeader("X-Cloud-Provider") cloudProvider: CloudProviderName,
                       @RequestHeader(value = "X-API-Credentials", required = false) apiCredentials: String="",
                       @RequestBody resourceToUpdate: Any ){
        return restHelper.updateResource(cloudProvider,this.getResourceType(resourceName), apiCredentials, resourceToUpdate)
    }


    @DeleteMapping("{resource}/{reference}")
    fun deleteResource(@PathVariable("resource") resourceName: String,
                       @PathVariable("reference") vendorReference: String,
                       @RequestHeader("X-Request-ID") requestId: String,
                       @RequestHeader("X-Cloud-Provider") cloudProvider: CloudProviderName,
                       @RequestHeader(value = "X-API-Credentials", required = false) apiCredentials: String="",
                       @RequestBody(required = false) resourceToDelete: Any ){
        return restHelper.deleteResource(cloudProvider,this.getResourceType(resourceName), apiCredentials, resourceToDelete)
    }



    private fun getResourceType(resourceName: String): ResourceType {
        when(resourceName.toLowerCase()){
            "virtualmachines" ->  return ResourceType.VirtualMachine
            "sshkeys" ->  return ResourceType.SSHKey
            "networks" ->  return ResourceType.Network
            "floatingips" ->  return ResourceType.FloatingIP
            "virtualmachinetypes" -> return ResourceType.VirtualMachineType
            "osimages" -> return ResourceType.OperatingSystemImage
            "locations" ->  return ResourceType.Location

        }

        return ResourceType.None

    }

}

