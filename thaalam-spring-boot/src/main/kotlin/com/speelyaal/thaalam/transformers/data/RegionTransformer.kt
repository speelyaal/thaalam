package com.speelyaal.thaalam.transformers.data

import org.springframework.util.ResourceUtils

class RegionTransformer {

    fun readConfig(){
       var configFile = ResourceUtils.getFile("classpath:mappers/hetzner/responses/region.yml")

        configFile.forEachLine {
            println(it);
        }

    }


}

fun main() {
    var regionTransformer= RegionTransformer()
    regionTransformer.readConfig();
}