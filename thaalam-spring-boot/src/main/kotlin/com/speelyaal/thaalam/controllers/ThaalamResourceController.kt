package com.speelyaal.thaalam.controllers

import com.speelyaal.thaalam.datamodel.CloudProviderList
import com.speelyaal.thaalam.datamodel.Region
import com.speelyaal.thaalam.transformers.data.RequestTransformer
import com.speelyaal.thaalam.transformers.data.ResponseTransformer
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("resources")
class ThaalamResourceController(var requestTransformer: RequestTransformer,
                                var responseTransformer: ResponseTransformer){


    @GetMapping("{resource}")
    fun getAllResources(@PathVariable("resource") resource: String, @RequestHeader("X-Request-ID") requestId: String,
                        @RequestHeader("X-Cloud-Provider") cloudProvider: CloudProviderList,
                        @RequestHeader(value = "X-API-Token", required = false) apiToken: String="" ): ArrayList<Any> {


        requestTransformer.test();
        return ArrayList()

    }

}