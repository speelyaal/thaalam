package com.speelyaal.thaalam.datamodel

import com.fasterxml.jackson.annotation.JsonInclude
import com.speelyaal.thaalam.config.ConfigLoader
import com.speelyaal.thaalam.transformers.utils.DateUtil
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.lang.Exception
import java.lang.reflect.Type
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.javaType

@JsonInclude(JsonInclude.Include.NON_NULL)
abstract class ThaalamResource  {

    private val LOG: Logger = LogManager.getLogger(this::class.java)

    // Thaalam identifier, used for logging and tracking
    var id: String? = null
    var name: String? = null
    var description: String? = null
    var tags: List<String>? = null
    var createdDateTime: Date? = null
    var lastModifiedDateTime: Date? = null

    //Vendor identifier, some providers use id some use name. So created a common field called vendorReference
    var vendorReference: String? = null

    fun setProperty(fieldName: String, value: Any) {


        val kClass = Class.forName(this::class.java.name).kotlin
        val instance = this;
        var valueToSet: Any = value;

        val member = kClass.memberProperties.filterIsInstance<KMutableProperty<*>>()
                .firstOrNull { it.name == fieldName }


        try {
            valueToSet = when(member?.getter?.returnType?.classifier) {
                Date::class -> {
                    //FIXME: Try to remove this split, this is done for a workaround
                    // This is done because teh created date from Hetzner could not be parsed  Text '2019-12-24T09:26:48+00:00' could not be parsed, unparsed text found at index 19
                    DateUtil.asDate(LocalDateTime.parse(value.toString().split("+")[0]))
                }
                //TODO: Do proper casting avoid toString then toDouble
                Int::class -> value.toString().toInt()
                Integer::class -> value.toString().toDouble()
                Float::class -> value.toString().toDouble()
                Double::class -> value.toString().toDouble()

                else ->value.toString();

            }!!

        }catch (exception: Exception){
            LOG.error("Error changing type , or parsing value")
            LOG.error(exception.message)
        }


        try {
            member?.setter?.call(instance, valueToSet)
        } catch (exception: IllegalArgumentException) {
            LOG.error(" Exception setting $fieldName with value $value")
            LOG.error(exception.message)
        }


    }


    fun getPropertyValue(fieldName: String): Any? {


        val kClass = Class.forName(this::class.java.name).kotlin
        val instance = this;

        val member = kClass.memberProperties.filterIsInstance<KMutableProperty<*>>()
                .firstOrNull { it.name == fieldName }

        try {
           return member?.getter?.call(instance)
        } catch (exception: IllegalArgumentException) {
            LOG.error(" Exception getting value for  $fieldName ")
            LOG.error(exception.message)
        }

        return null
    }




}