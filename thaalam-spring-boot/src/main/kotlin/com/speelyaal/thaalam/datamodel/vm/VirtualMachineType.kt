package com.speelyaal.thaalam.datamodel.vm

data class VirtualMachineType(var id: String) {
    var vendorReference: String =""
    var description: String =""
    var cores: Int = 0
    var memory: Int =0
    var disk: Int = 0
}