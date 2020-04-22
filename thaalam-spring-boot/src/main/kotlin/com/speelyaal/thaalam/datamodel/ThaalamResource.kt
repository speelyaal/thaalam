package com.speelyaal.thaalam.datamodel

import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.memberProperties

open class ThaalamResource {




    fun setProperty(fieldName: String, value: Any) {

        println("Field Name : $fieldName   Value: $value" )
        val kClass = Class.forName(this::class.java.name).kotlin
       // val instance = kClass.objectInstance ?: kClass.java.getDeclaredConstructor().newInstance()
        val instance = this;

       // val kClass = this::javaClass

        val member = kClass.memberProperties.filterIsInstance<KMutableProperty<*>>()
                .firstOrNull { it.name == fieldName }

        member?.setter?.call(instance, value)
    }

    fun printClassName() {
       // println("Class Name is    _____________________ " + this::class.java.name)
    }


}