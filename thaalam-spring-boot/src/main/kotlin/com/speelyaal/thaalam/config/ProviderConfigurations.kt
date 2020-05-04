package com.speelyaal.thaalam.config

import com.speelyaal.thaalam.datamodel.ResourceName
import com.speelyaal.thaalam.transformers.utils.ResourceType

class ProviderConfigurations {

    //Properties with default values
    var apiVersion: String = "v1"
    var apiUrl: String ="[URL]"
    var authorizationType: AuthorizationType = AuthorizationType.bearerToken
    var resources: List<ResourceType> = arrayListOf(
            ResourceType.Region,
            ResourceType.OperatingSystemImage,
            ResourceType.VirtualMachineType,
            ResourceType.VirtualMachine,
            ResourceType.SSHKey
    )

    enum class AuthorizationType {
        bearerToken,
        basicAuth

    }


}

