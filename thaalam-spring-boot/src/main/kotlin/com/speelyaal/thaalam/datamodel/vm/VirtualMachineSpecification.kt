package com.speelyaal.thaalam.datamodel.vm

data class VirtualMachineSpecification(var vcpus : Int =0 ) {
    var disk: Int = 0
    var memory: Int = 0

}