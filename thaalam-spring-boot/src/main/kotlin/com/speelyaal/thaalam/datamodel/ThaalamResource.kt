package com.speelyaal.thaalam.datamodel

import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.memberProperties

open class ThaalamResource {




    fun setProperty(fieldName: String, value: Any) {


        val kClass = Class.forName(this::class.java.name).kotlin
       // val instance = kClass.objectInstance ?: kClass.java.getDeclaredConstructor().newInstance()
        val instance = this;

       // val kClass = this::javaClass

        val member = kClass.memberProperties.filterIsInstance<KMutableProperty<*>>()
                .firstOrNull { it.name == fieldName }


            member?.setter?.call(instance, value.toString())




    }




}