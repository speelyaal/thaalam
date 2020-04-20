package com.speelyaal.thaalam.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
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

    var providerConfigurations:
            HashMap<String, ProviderConfigurations> = HashMap()

    var requestObjectMappers:
            HashMap<String, HashMap<String, RequestMapper>> = HashMap()

    var responseObjectMappers:
            HashMap<String, HashMap<String, ResponseMapper>> = HashMap()

    @PostConstruct
    fun loadConfigurations() {
        thaalamProperties.providers.forEach {
            try {
                var providerConfig = ResourceUtils.getFile("classpath:providers/$it/config.yml");
                val mapper = ObjectMapper(YAMLFactory())
                var tmpProviderConfiguration: ProviderConfigurations =
                        mapper.readValue(providerConfig, ProviderConfigurations::class.java)

                this.providerConfigurations[it] = tmpProviderConfiguration

            } catch (fileNotFound: FileNotFoundException) {
                println("Config file not found for $it")
            }


        }

        this.loadRequestMappers();


    }

    fun loadRequestMappers(){
        this.providerConfigurations.forEach{

            it.value.resources.forEach {
                println("Resource    $it" )
            }



        }
    }


}