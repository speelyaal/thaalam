package com.speelyaal.thaalam.controllers

import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.databind.ObjectMapper
import com.speelyaal.thaalam.datamodel.CloudProviderName
import com.speelyaal.thaalam.datamodel.ResourceName
import com.speelyaal.thaalam.datamodel.vm.VirtualMachine
import com.speelyaal.thaalam.transformers.utils.RestHelper
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("resources")
class ThaalamResourceController(var restHelper: RestHelper){


    private val jsonObjectMapper: ObjectMapper = ObjectMapper(JsonFactory());

    //FIXME: Rename API-Token to Auth token
    @GetMapping("{resource}")
    fun getAllResources(@PathVariable("resource") resourceName: ResourceName,
                        @RequestHeader("X-Request-ID") requestId: String,
                        @RequestHeader("X-Cloud-Provider") cloudProvider: CloudProviderName,
                        @RequestHeader(value = "X-API-Credentials", required = false) apiCredentials: String="" ): Any {
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



        return restHelper.createResource(cloudProvider,resourceName,apiCredentials, this.castToResourceType(resourceToCreate))
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
    private fun castToResourceType(resourceToCreate: Any): Any {

        //TODO: Cast to appropriate type: example to Virtual Machine
        var jsonString = this.jsonObjectMapper.writeValueAsString(resourceToCreate)
        //TODO: get type from

        return this.jsonObjectMapper.readValue(jsonString, VirtualMachine::class.java )

    }

}

