package com.speelyaal.thaalam.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "thaalam")
class ThaalamProperties {
    lateinit var version: String
    lateinit var providers: Array<String>



}
