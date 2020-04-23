package com.speelyaal.thaalam.config

import com.speelyaal.thaalam.datamodel.ResourceName

class ProviderConfigurations {

    //Properties with default values
    var apiVersion: String = "v1"
    var apiUrl: String ="[URL]"
    var authorizationType: AuthorizationType = AuthorizationType.bearerToken
    var resources: List<ResourceName> = arrayListOf(
            ResourceName.regions,
            ResourceName.osImages,
            ResourceName.virtualMachineTypes,
            ResourceName.virtualMachines,
            ResourceName.sshKeys
    )

    enum class AuthorizationType {
        bearerToken,
        basicAuth

    }


}

