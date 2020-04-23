package com.speelyaal.thaalam.controllers

import com.speelyaal.thaalam.datamodel.CloudProviderName
import com.speelyaal.thaalam.datamodel.ResourceName
import com.speelyaal.thaalam.transformers.utils.RestHelper
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("resources")
class ThaalamResourceController(var restHelper: RestHelper){


    //FIXME: Rename API-Token to Auth token
    @GetMapping("{resource}")
    fun getAllResources(@PathVariable("resource") resource: ResourceName, @RequestHeader("X-Request-ID") requestId: String,
                        @RequestHeader("X-Cloud-Provider") cloudProvider: CloudProviderName,
                        @RequestHeader(value = "X-API-Token", required = false) apiToken: String="" ): Any {
        return restHelper.getResources(cloudProvider,resource,apiToken)

    }

    @GetMapping("{resource}/{reference}")
    fun getResourceByReference(@PathVariable("resource") resource: ResourceName,
                               @PathVariable("reference") vendorReference: String,
                               @RequestHeader("X-Request-ID") requestId: String,
                               @RequestHeader("X-Cloud-Provider") cloudProvider: CloudProviderName,
                               @RequestHeader(value = "X-API-Token", required = false) apiToken: String=""
                               ): Any {
        return restHelper.getResourceByReference(cloudProvider,resource,apiToken, vendorReference)

    }

}