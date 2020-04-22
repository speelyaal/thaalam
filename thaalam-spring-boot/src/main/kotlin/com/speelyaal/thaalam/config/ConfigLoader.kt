package com.speelyaal.thaalam.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.speelyaal.thaalam.datamodel.CloudProviderList
import com.speelyaal.thaalam.transformers.data.RequestMapper
import com.speelyaal.thaalam.transformers.data.ResponseMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.util.ResourceUtils
import java.io.FileNotFoundException
import javax.annotation.PostConstruct

@Component
class ConfigLoader {

    @Autowired
    lateinit var thaalamProperties: ThaalamProperties

    private val objectMapper = ObjectMapper(YAMLFactory())

    var providerConfigurations:
            HashMap<CloudProviderList, ProviderConfigurations> = HashMap()

    var requestObjectMappers:
            HashMap<CloudProviderList, HashMap<String, RequestMapper>> = HashMap()

    var responseObjectMappers:
            HashMap<CloudProviderList, HashMap<String, ResponseMapper>> = HashMap()

    @PostConstruct
    fun loadConfigurations() {
        thaalamProperties.providers.forEach { it
            var cloudProvider: CloudProviderList = CloudProviderList.valueOf(it)
            try {
                var providerConfig = ResourceUtils.getFile("classpath:providers/$cloudProvider/config.yml");

                var tmpProviderConfiguration: ProviderConfigurations =
                        objectMapper.readValue(providerConfig, ProviderConfigurations::class.java)

                this.providerConfigurations[cloudProvider] = tmpProviderConfiguration

            } catch (fileNotFound: FileNotFoundException) {
                println("Config file not found for $it")
            }


        }

        this.loadRequestMappers();

        this.loadResponseMappers();


    }

    private fun loadResponseMappers() {
        this.providerConfigurations.forEach{
            it.value.resources.forEach {
                println("Resource    $it" )
            }
        }
    }

    private fun loadRequestMappers(){
        this.providerConfigurations.forEach{ providerConfig ->
            var providerName = providerConfig.key;
            var tempResourceMap : HashMap<String, RequestMapper> = HashMap()

            providerConfig.value.resources.forEach { resrouce ->
                try {

                    println("Provider config path is   providers/$providerName/resources/$resrouce/request.yml" )
                    var requestConfigFile = ResourceUtils.getFile("classpath:providers/$providerName/resources/$resrouce/request.yml");
                    var tmpRequestMapper: RequestMapper =
                            objectMapper.readValue(requestConfigFile, RequestMapper::class.java)

                    tempResourceMap[resrouce] = tmpRequestMapper;
                } catch (exception: FileNotFoundException){
                    println("Request mapper file not found")
                }
            }
            this.requestObjectMappers[providerName]=tempResourceMap;
        }
    }


}