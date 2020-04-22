package com.speelyaal.thaalam.transformers.responses

import com.fasterxml.jackson.databind.ObjectMapper
import com.jayway.jsonpath.JsonPath
import com.speelyaal.thaalam.config.ConfigLoader
import com.speelyaal.thaalam.datamodel.CloudProviderList
import com.speelyaal.thaalam.datamodel.ThaalamResource
import com.speelyaal.thaalam.transformers.exceptions.ResponseMapperNotFoundException
import com.speelyaal.thaalam.transformers.utils.ResourceTypeFactory
import net.minidev.json.JSONArray
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component


@Component
class ResponseTransformer {

    @Autowired
    lateinit var config: ConfigLoader

    fun transformListResponse(cloudProvider: CloudProviderList, resource: String, result: ResponseEntity<String>): Any {

        var responseMapper = this.getResponseMapper(cloudProvider, resource)

        var mapping = responseMapper.mapping

        //TODO: Actual stuff goes here, create  new Response Type, map properties and put them in list

        var listResult: JSONArray = JsonPath.parse(result.body).read(responseMapper.listPath)

       // var responseList: List<ThaalamResource>? = ResourceTypeFactory.getListInstance(responseMapper.thaalamType)
        var responseList = ArrayList<Any>()
        listResult.forEach { region ->

            var om: ObjectMapper = ObjectMapper();
            var jsonObject = om.writeValueAsString(region);
            println(jsonObject)

            var tmpObject= ResourceTypeFactory.getInstance(responseMapper.thaalamType);

            mapping.forEach { property ->

                var tempVal: String = JsonPath.parse(jsonObject).read(property.value);
                //println("Class is  " + tmpObject!!::class.java)
                tmpObject?.printClassName();
                tmpObject?.setProperty(property.key, tempVal)
                println("${property.key}     -   $tempVal")
                if (tmpObject != null) {
                    responseList.add(tmpObject)
                }

            }


        }


        return responseList

    }

    private fun getResponseMapper(cloudProvider: CloudProviderList, resource: String): ResponseMapper {

        this.config.responseObjectMappers[cloudProvider].let {
            var responseMapper = it?.get(resource)
            if(responseMapper != null){
                return responseMapper
            }else{
                //TODO: Log the problem with finding Response mapper
                throw ResponseMapperNotFoundException
            }
        }

    }


  /*  @Suppress("UNCHECKED_CAST")
    fun <R> getProperty(instance: Any, propertyName: String): R {
        val property = instance::class.memberProperties
                // don't cast here to <Any, R>, it would succeed silently
                .first { it.name == propertyName } as KProperty1<Any, *>

        // force a invalid cast exception if incorrect type here
        return property.get(instance) as R
    }*/


}