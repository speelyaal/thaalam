package com.speelyaal.thaalam.config

import org.springframework.util.ResourceUtils
import java.io.File

class ConfigFilesHelper {


}

fun main(args:Array<String>) {
    var PROVIDERS_ROOT_PATH = args[0]
    var file = File("$PROVIDERS_ROOT_PATH/hetzner/config2.yml")

    if (!file.isFile) {
        file.createNewFile()
    } else {


        file.forEachLine {
            println(it);
        }
    }
}