package com.speelyaal.thaalam.transformers.data

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.speelyaal.thaalam.config.ConfigLoader
import com.speelyaal.thaalam.config.ProviderConfigurations

import com.speelyaal.thaalam.config.ThaalamProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.util.ResourceUtils
import java.io.FileNotFoundException
import javax.annotation.PostConstruct


@Component
class RequestTransformer {


    @Autowired
    lateinit var config:  ConfigLoader



    fun test(){

        this.config.providerConfigurations.forEach{

            it.value.resources.forEach {
                println("Resource    $it" )
            }



        }

    }

}

