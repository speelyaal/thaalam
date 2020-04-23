package com.speelyaal.thaalam.transformers.utils

import com.speelyaal.thaalam.config.ConfigLoader
import com.speelyaal.thaalam.datamodel.CloudProviderName
import com.speelyaal.thaalam.datamodel.Region
import com.speelyaal.thaalam.datamodel.ResourceName
import com.speelyaal.thaalam.transformers.requests.RequestMapper
import com.speelyaal.thaalam.transformers.requests.RequestTransformer
import com.speelyaal.thaalam.transformers.responses.ResponseTransformer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate


//FIXME: Logger: Logging to be done
@Component
class RestHelper {

    @Autowired
    private lateinit var config: ConfigLoader

    @Autowired
    private lateinit var requestTransformer: RequestTransformer

    @Autowired
    lateinit var responseTransformer: ResponseTransformer

    private var restTemplate = RestTemplate()


    fun getResources(cloudProvider: CloudProviderName, resource: ResourceName, apiToken: String): Any {


        //TODO: Error handling when Request Mapper is not found
        var requestMapper: RequestMapper? = this.config.requestObjectMappers[cloudProvider]?.get(resource)


        var apiUrl = this.config.providerConfigurations[cloudProvider]?.apiUrl


        //Set the headers you need send
        //Set the headers you need send
        val headers = HttpHeaders()
        headers.set("Authorization", "Bearer " + apiToken)

        //Create a new HttpEntity
        val entity = HttpEntity<String>(headers)
        var result = restTemplate.exchange(apiUrl + requestMapper?.getAll?.path, HttpMethod.GET, entity, String::class.java)

        //TODO:



       return this.responseTransformer.transformListResponse(cloudProvider, resource, result )
    }

    fun getResourceByReference(cloudProvider: CloudProviderName, resource: ResourceName, apiToken: String, vendorReference: String): Any {



        //TODO: Error handling when Request Mapper is not found
        var requestMapper: RequestMapper? = this.config.requestObjectMappers[cloudProvider]?.get(resource)


        var apiUrl = this.config.providerConfigurations[cloudProvider]?.apiUrl



        //Set the headers you need send
        val headers = HttpHeaders()
        headers.set("Authorization", "Bearer " + apiToken)

        //Create a new HttpEntity
        val entity = HttpEntity<String>(headers)
        val path = requestMapper?.getByReference?.path.toString().replace("{reference}", vendorReference)
        val method =  requestMapper?.getByReference?.method as HttpMethod

        var result = restTemplate.exchange(apiUrl + path, method , entity, Any::class.java)

        //TODO:


        return Region();
    }

}