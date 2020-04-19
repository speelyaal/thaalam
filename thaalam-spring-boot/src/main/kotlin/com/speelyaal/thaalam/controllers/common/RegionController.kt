package com.speelyaal.thaalam.controllers.common

import com.speelyaal.thaalam.datamodel.CloudProviderList
import com.speelyaal.thaalam.datamodel.Region
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("regions")
class RegionController {

    @GetMapping()
    fun getAllRegions(@RequestHeader("X-Request-ID") requestId: String,
                      @RequestHeader("X-Cloud-Provider") cloudProvider: CloudProviderList ,
                      @RequestHeader(value = "X-API-Token", required = false) apiToken: String="" ): ArrayList<Region> {

        return ArrayList<Region>()

    }
}