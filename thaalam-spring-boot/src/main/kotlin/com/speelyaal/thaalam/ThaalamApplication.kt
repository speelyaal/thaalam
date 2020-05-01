package com.speelyaal.thaalam

import com.speelyaal.thaalam.config.ThaalamProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(ThaalamProperties::class)
class ThaalamApplication

fun main(args: Array<String>) {
	runApplication<ThaalamApplication>(*args)
}
