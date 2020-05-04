package com.speelyaal.thaalam.transformers.utils

import com.speelyaal.thaalam.config.ConfigLoader
import com.speelyaal.thaalam.config.ProviderConfigurations
import com.speelyaal.thaalam.datamodel.CloudProviderName
import com.speelyaal.thaalam.datamodel.ResourceType
import com.speelyaal.thaalam.transformers.requests.RequestMapper
import com.speelyaal.thaalam.transformers.requests.RequestTransformer
import com.speelyaal.thaalam.transformers.responses.ResponseTransformer
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.lang.IllegalArgumentException


//FIXME: Logger: Logging to be done
@Component
class RestHelper {

    private val LOG: Logger = LogManager.getLogger(RestHelper::class.java)

    @Autowired
    private lateinit var config: ConfigLoader

    @Autowired
    private lateinit var requestTransformer: RequestTransformer

    @Autowired
    lateinit var responseTransformer: ResponseTransformer

    private var restTemplate = RestTemplate()


    fun getResources(cloudProvider: CloudProviderName, resourceType: ResourceType, apiCredentials: String): Any {


        //TODO: Error handling when Request Mapper is not found
        var requestMapper: RequestMapper? = this.config.requestObjectMappers[cloudProvider]?.get(resourceType)


        var apiUrl = this.config.providerConfigurations[cloudProvider]?.apiUrl

        val entity = this.getHttpEntity(cloudProvider, apiCredentials)


        var result: ResponseEntity<String>

        try {
            result = restTemplate.exchange(apiUrl + requestMapper?.getAll?.path, HttpMethod.GET, entity, String::class.java)
            return this.responseTransformer.transformListResponse(cloudProvider, resourceType, result)
        }catch (exception: IllegalArgumentException){
            LOG.error(exception.message)
            LOG.error("URL is : " + apiUrl + requestMapper?.getAll?.path)
            exception.printStackTrace()

            //TODO: Thaalam REST Exception
            throw IllegalArgumentException()
        }





    }

    fun getResourceByReference(cloudProvider: CloudProviderName, resourceType: ResourceType, apiCredentials: String, vendorReference: String): Any {


        var requestMapper = this.getRequestMapper(cloudProvider, resourceType)
        var apiUrl = this.config.providerConfigurations[cloudProvider]?.apiUrl

        val entity = this.getHttpEntity(cloudProvider, apiCredentials)
        val method = requestMapper.getByReference
        val url = apiUrl + method.path.replace("{reference}", vendorReference)

        var result = restTemplate.exchange(url, method.method, entity, Any::class.java)



        return this.responseTransformer.transformSingleResourceResponse(cloudProvider, resourceType, result)
    }

    private fun getRequestMapper(cloudProvider: CloudProviderName, resourceType: ResourceType): RequestMapper {

        //TODO: Error handling when Request Mapper is not found
        return this.config.requestObjectMappers[cloudProvider]?.get(resourceType)!!
    }

    fun createResource(cloudProvider: CloudProviderName,
                       resourceType: ResourceType,
                       apiCredentials: String,
                       resourceToCreate: Any): Any {

        var requestMapper = this.getRequestMapper(cloudProvider, resourceType)
        var apiUrl = this.config.providerConfigurations[cloudProvider]?.apiUrl


        val method = requestMapper.create
        val url = apiUrl + method.path

        var resourceToPost =
                this.requestTransformer.transformResourceToPost(
                        cloudProvider,
                        resourceType,
                        resourceToCreate,
                        method.body)


        val entity = this.getHttpEntity(cloudProvider, apiCredentials, resourceToPost)


        var result = this.restExchange(url, method.method, entity, Any::class.java)




        return this.responseTransformer.transformSingleResourceResponse(cloudProvider, resourceType, result)

    }

    fun updateResource(cloudProvider: CloudProviderName, resourceType: ResourceType, apiCredentials: String, resourceToUpdate: Any) {

    }

    fun deleteResource(cloudProvider: CloudProviderName, resourceType: ResourceType, apiCredentials: String, resourceToDelete: Any) {

        //TODO: This(Delete) has to be handle differently for cloud providers
        //For example: Hetzner always sends 200 with action object inside with status whereas Linode sends 200 for succss and default to error

    }

    private fun restExchange(url: String, method: HttpMethod, entity: HttpEntity<Any>, java: Class<Any>): ResponseEntity<Any> {

        //TODO: Exception Handling and Logging

        var result = restTemplate.exchange(
                url,
                method,
                entity,
                Any::class.java)
        return result

    }

    private  fun getHttpEntity(cloudProvider: CloudProviderName, apiCredentials: String): HttpEntity<String> {
        val headers = HttpHeaders()

        val authType: String = when (this.config.providerConfigurations[cloudProvider]?.authorizationType) {
            ProviderConfigurations.AuthorizationType.bearerToken -> "Bearer"
            ProviderConfigurations.AuthorizationType.basicAuth -> "Basic"
            else -> ""
        }
        headers.set("Authorization", "$authType $apiCredentials")
        return  HttpEntity<String>(headers)
    }


    //TODO: Cleanup duplicate code
    private  fun getHttpEntity(cloudProvider: CloudProviderName, apiCredentials: String, body: Any): HttpEntity<Any> {
        val headers = HttpHeaders()

        val authType: String = when (this.config.providerConfigurations[cloudProvider]?.authorizationType) {
            ProviderConfigurations.AuthorizationType.bearerToken -> "Bearer"
            ProviderConfigurations.AuthorizationType.basicAuth -> "Basic"
            else -> ""
        }
        headers.set("Authorization", "$authType $apiCredentials")
        return  HttpEntity<Any>(body,headers)
    }



}