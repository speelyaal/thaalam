package com.speelyaal.thaalam.transformers.utils

import com.speelyaal.thaalam.datamodel.Region
import com.speelyaal.thaalam.datamodel.ThaalamResource

class ResourceTypeFactory {

    enum class ResourceTypes {
        Region,
        VirtualMachine,
        None
    }

    companion object {

        fun getInstance(resource: ResourceTypes): ThaalamResource? {

            when (resource) {
                ResourceTypes.Region -> return Region()
                else -> {

                    println("Resource not found")
                    //TODO: LOG problem
                }
            }

            return null;

        }

     /*   fun  getListInstance(resource: ResourceTypes): ArrayList<Any>? {
            when (resource) {
                ResourceTypes.Region -> return ArrayList<Region>()
                else -> {

                    println("Resource not found")
                    //TODO: LOG problem
                }
            }

            return null;

        }*/
    }
}