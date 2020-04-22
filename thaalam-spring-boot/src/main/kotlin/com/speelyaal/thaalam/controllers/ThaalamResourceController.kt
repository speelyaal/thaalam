package com.speelyaal.thaalam.controllers

import com.speelyaal.thaalam.datamodel.CloudProviderList
import com.speelyaal.thaalam.datamodel.Region
import com.speelyaal.thaalam.transformers.data.RequestMapper
import com.speelyaal.thaalam.transformers.data.RequestTransformer
import com.speelyaal.thaalam.transformers.data.ResponseTransformer
import com.speelyaal.thaalam.transformers.utils.RestHelper
import org.springframework.http.HttpMethod
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("resources")
class ThaalamResourceController(var restHelper: RestHelper){


    //FIXME: Rename API-Token to Auth token
    @GetMapping("{resource}")
    fun getAllResources(@PathVariable("resource") resource: String, @RequestHeader("X-Request-ID") requestId: String,
                        @RequestHeader("X-Cloud-Provider") cloudProvider: CloudProviderList,
                        @RequestHeader(value = "X-API-Token", required = false) apiToken: String="" ): Any {
        return restHelper.getResources(cloudProvider,resource,apiToken)

    }

    @GetMapping("{resource}/{reference}")
    fun getResourceByReference(@PathVariable("resource") resource: String,
                               @PathVariable("reference") vendorReference: String,
                               @RequestHeader("X-Request-ID") requestId: String,
                               @RequestHeader("X-Cloud-Provider") cloudProvider: CloudProviderList,
                               @RequestHeader(value = "X-API-Token", required = false) apiToken: String=""
                               ): Any {
        return restHelper.getResourceByReference(cloudProvider,resource,apiToken, vendorReference)

    }

}