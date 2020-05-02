package com.speelyaal.thaalam.controllers

import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.databind.ObjectMapper
import com.speelyaal.thaalam.datamodel.CloudProviderName
import com.speelyaal.thaalam.datamodel.ResourceName
import com.speelyaal.thaalam.datamodel.network.FloatingIP
import com.speelyaal.thaalam.datamodel.network.Network
import com.speelyaal.thaalam.datamodel.vm.SSHKey
import com.speelyaal.thaalam.datamodel.vm.VirtualMachine
import com.speelyaal.thaalam.transformers.responses.ResponseTransformer
import com.speelyaal.thaalam.transformers.utils.RestHelper
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("resources")
class ThaalamResourceController(var restHelper: RestHelper){


    private val LOG: Logger = LogManager.getLogger(ThaalamResourceController::class.java)

    private val jsonObjectMapper: ObjectMapper = ObjectMapper(JsonFactory());


    @GetMapping("{resource}")
    fun getAllResources(@PathVariable("resource") resourceName: ResourceName,
                        @RequestHeader("X-Request-ID") requestId: String,
                        @RequestHeader("X-Cloud-Provider") cloudProvider: CloudProviderName,
                        @RequestHeader(value = "X-API-Credentials", required = false) apiCredentials: String="" ): Any {


        LOG.debug("Get resources.... ${resourceName} - ${cloudProvider} -- ${apiCredentials} -- ${requestId}")

        return restHelper.getResources(cloudProvider,resourceName,apiCredentials)

    }

    @GetMapping("{resource}/{reference}")
    fun getResourceByReference(@PathVariable("resource") resourceName: ResourceName,
                               @PathVariable("reference") vendorReference: String,
                               @RequestHeader("X-Request-ID") requestId: String,
                               @RequestHeader("X-Cloud-Provider") cloudProvider: CloudProviderName,
                               @RequestHeader(value = "X-API-Credentials", required = false) apiCredentials: String=""
                               ): Any {
        return restHelper.getResourceByReference(cloudProvider,resourceName,apiCredentials, vendorReference)

    }


    @PostMapping("{resource}")
    fun createResource(@PathVariable("resource") resourceName: ResourceName,
                       @RequestHeader("X-Request-ID") requestId: String,
                       @RequestHeader("X-Cloud-Provider") cloudProvider: CloudProviderName,
                       @RequestHeader(value = "X-API-Credentials", required = false) apiCredentials: String="",
                       @RequestBody resourceToCreate: Any ): Any{



        return restHelper.createResource(cloudProvider,resourceName,apiCredentials, this.castToResourceType(resourceName,resourceToCreate))
    }

    @PutMapping("{resource}/{reference}")
    fun updateResource(@PathVariable("resource") resourceName: ResourceName,
                       @PathVariable("reference") vendorReference: String,
                       @RequestHeader("X-Request-ID") requestId: String,
                       @RequestHeader("X-Cloud-Provider") cloudProvider: CloudProviderName,
                       @RequestHeader(value = "X-API-Credentials", required = false) apiCredentials: String="",
                       @RequestBody resourceToUpdate: Any ){
        return restHelper.updateResource(cloudProvider,resourceName, apiCredentials, resourceToUpdate)
    }


    @DeleteMapping("{resource}/{reference}")
    fun deleteResource(@PathVariable("resource") resourceName: ResourceName,
                       @PathVariable("reference") vendorReference: String,
                       @RequestHeader("X-Request-ID") requestId: String,
                       @RequestHeader("X-Cloud-Provider") cloudProvider: CloudProviderName,
                       @RequestHeader(value = "X-API-Credentials", required = false) apiCredentials: String="",
                       @RequestBody(required = false) resourceToDelete: Any ){
        return restHelper.deleteResource(cloudProvider,resourceName, apiCredentials, resourceToDelete)
    }

    private fun castToResourceType(resourceName: ResourceName,resourceToCreate: Any): Any {


        var jsonString = this.jsonObjectMapper.writeValueAsString(resourceToCreate)


        when(resourceName){
            ResourceName.virtualMachines ->  return this.jsonObjectMapper.readValue(jsonString, VirtualMachine::class.java )
            ResourceName.sshKeys ->  return this.jsonObjectMapper.readValue(jsonString, SSHKey::class.java )
            ResourceName.networks ->  return this.jsonObjectMapper.readValue(jsonString, Network::class.java )
            ResourceName.floatingIPs ->  return this.jsonObjectMapper.readValue(jsonString, FloatingIP::class.java )

        }

        return this.jsonObjectMapper.readValue(jsonString, VirtualMachine::class.java )

    }

}

