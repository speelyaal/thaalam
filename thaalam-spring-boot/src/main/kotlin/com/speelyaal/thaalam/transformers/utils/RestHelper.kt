package com.speelyaal.thaalam.transformers.utils

import com.speelyaal.thaalam.config.ConfigLoader
import com.speelyaal.thaalam.datamodel.CloudProviderList
import com.speelyaal.thaalam.datamodel.Region
import com.speelyaal.thaalam.transformers.data.RequestMapper
import com.speelyaal.thaalam.transformers.data.RequestTransformer
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
    lateinit var responseTransformer: RequestTransformer

    private var restTemplate = RestTemplate()


    fun getResources(cloudProvider: CloudProviderList, resource: String, apiToken: String): Any {

        println("$cloudProvider, $resource, $apiToken")

        //TODO: Error handling when Request Mapper is not found
        var requestMapper: RequestMapper? = this.config.requestObjectMappers[cloudProvider]?.get(resource)

        println(requestMapper?.getAll?.path)
        var apiUrl = this.config.providerConfigurations[cloudProvider]?.apiUrl


        //Set the headers you need send
        //Set the headers you need send
        val headers = HttpHeaders()
        headers.set("Authorization", "Bearer " + apiToken)

        //Create a new HttpEntity
        val entity = HttpEntity<String>(headers)
        var result = restTemplate.exchange(apiUrl + requestMapper?.getAll?.path, HttpMethod.GET, entity, Any::class.java)

        //TODO:
        println(result);


       return ArrayList<String>()
    }

    fun getResourceByReference(cloudProvider: CloudProviderList, resource: String, apiToken: String, vendorReference: String): Any {


        println("$cloudProvider, $resource, $apiToken")

        //TODO: Error handling when Request Mapper is not found
        var requestMapper: RequestMapper? = this.config.requestObjectMappers[cloudProvider]?.get(resource)

        println(requestMapper?.getByReference?.path)
        var apiUrl = this.config.providerConfigurations[cloudProvider]?.apiUrl


        //Set the headers you need send
        //Set the headers you need send
        val headers = HttpHeaders()
        headers.set("Authorization", "Bearer " + apiToken)

        //Create a new HttpEntity
        val entity = HttpEntity<String>(headers)
        val path = requestMapper?.getByReference?.path.toString().replace("{reference}", vendorReference)
        println("Path is   $path")
        var result = restTemplate.exchange(apiUrl + path, HttpMethod.GET, entity, Any::class.java)

        //TODO:
        println(result);

        return Region("1");
    }

}