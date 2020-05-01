package com.speelyaal.thaalam.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.exc.InvalidFormatException
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.speelyaal.thaalam.ThaalamApplication
import com.speelyaal.thaalam.datamodel.CloudProviderName
import com.speelyaal.thaalam.datamodel.ResourceName
import com.speelyaal.thaalam.transformers.requests.RequestMapper
import com.speelyaal.thaalam.transformers.responses.ResponseMapper
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.system.ApplicationHome
import org.springframework.stereotype.Component
import org.springframework.util.ResourceUtils
import java.io.FileNotFoundException
import javax.annotation.PostConstruct


@Component
class ConfigLoader {

    private val LOG: Logger = LogManager.getLogger(ConfigLoader::class.java)
    private val REQUEST_FILE_NAME = "request.yml"
    private val RESPONSE_FILE_NAME = "response.yml"

    @Autowired
    lateinit var thaalamProperties: ThaalamProperties

    private val objectMapper = ObjectMapper(YAMLFactory())

    var providerConfigurations:
            HashMap<CloudProviderName, ProviderConfigurations> = HashMap()

    var requestObjectMappers:
            HashMap<CloudProviderName, HashMap<ResourceName, RequestMapper>> = HashMap()

    var responseObjectMappers:
            HashMap<CloudProviderName, HashMap<ResourceName, ResponseMapper>> = HashMap()

    @PostConstruct
    fun loadConfigurations() {

        val home = ApplicationHome(ThaalamApplication::class.java)
        LOG.info("Directory is " +   home.dir) // returns the folder where the jar is. This is what I wanted.
        LOG.info("Absolute Path is " + home.source)

        LOG.debug("")

        thaalamProperties.providers.forEach {
            var cloudProvider: CloudProviderName = CloudProviderName.valueOf(it)

            try {
                var providerConfig = ResourceUtils.getFile("${thaalamProperties.mappersPath}/$cloudProvider/config.yml");


                var tmpProviderConfiguration: ProviderConfigurations =
                        objectMapper.readValue(providerConfig, ProviderConfigurations::class.java)

                this.providerConfigurations[cloudProvider] = tmpProviderConfiguration

            } catch (fileNotFound: FileNotFoundException) {
                LOG.error("Config file not found for $cloudProvider")
                LOG.info("Config file create for $cloudProvider")
            } catch (invalidFormatException: InvalidFormatException){
                LOG.error("Invalid yaml for $cloudProvider")
                LOG.error(invalidFormatException.message)
            }


        }

        this.loadRequestMappers();

        this.loadResponseMappers();


    }

    private fun loadResponseMappers() {
        this.providerConfigurations.forEach{ providerConfig ->
            var providerName = providerConfig.key;
            var tempResourceMap : HashMap<ResourceName, ResponseMapper> = HashMap()

            providerConfig.value.resources.forEach { resrouce ->
                try {


                    var responseConfigFile =
                            ResourceUtils.getFile(
                                    "${thaalamProperties.mappersPath}/$providerName/resources/$resrouce/$RESPONSE_FILE_NAME");
                    var tmpResponseMapper: ResponseMapper =
                            objectMapper.readValue(responseConfigFile, ResponseMapper::class.java)

                    tempResourceMap[resrouce] = tmpResponseMapper;

                } catch (exception: FileNotFoundException){
                    LOG.error("Response mapper file not found (${thaalamProperties.mappersPath}/$providerName/resources/$resrouce/$RESPONSE_FILE_NAME)")
                }catch (exception: UnrecognizedPropertyException){
                    LOG.error("Problem with Yaml file(${thaalamProperties.mappersPath}/$providerName/resources/$resrouce/$RESPONSE_FILE_NAME)")

                }
            }
            this.responseObjectMappers[providerName]=tempResourceMap;
        }
    }

    private fun loadRequestMappers(){
        this.providerConfigurations.forEach{ providerConfig ->
            var providerName = providerConfig.key;
            var tempResourceMap : HashMap<ResourceName, RequestMapper> = HashMap()

            providerConfig.value.resources.forEach { resource ->
                try {

                    var requestConfigFile =
                            ResourceUtils.getFile(
                                    "${thaalamProperties.mappersPath}/$providerName/resources/$resource/$REQUEST_FILE_NAME");

                    var tmpRequestMapper: RequestMapper =
                            objectMapper.readValue(requestConfigFile, RequestMapper::class.java)

                    tempResourceMap[resource] = tmpRequestMapper;

                } catch (exception: FileNotFoundException){
                    LOG.error("Request mapper file not found (${thaalamProperties.mappersPath}/$providerName/resources/$resource/$REQUEST_FILE_NAME)")
                } catch (exception: UnrecognizedPropertyException){
                    LOG.error("Problem with Yaml file(${thaalamProperties.mappersPath}/$providerName/resources/$resource/$REQUEST_FILE_NAME)")
                    LOG.error(exception.message);

                }
            }
            this.requestObjectMappers[providerName]=tempResourceMap;
        }
    }


}