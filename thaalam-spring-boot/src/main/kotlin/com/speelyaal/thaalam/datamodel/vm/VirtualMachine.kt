package com.speelyaal.thaalam.datamodel.vm

import com.speelyaal.thaalam.datamodel.ThaalamResource
import com.speelyaal.thaalam.datamodel.vm.VirtualMachineStatus
import java.util.*
import kotlin.collections.ArrayList

class VirtualMachine : ThaalamResource() {
    var id: String = ""
    var createdDateTime: Date = Date()
    var lastModifiedDateTime: Date = Date()
    var labels : List<String> = ArrayList<String>()
    var osImageIdentifier: String =""
    var vmTypeIdentifier: String =""
    var vmRegionIdentifier: String = ""
    var group: String =""
    var tags: List<String> = ArrayList<String>()
    var ipv4: List<String> = ArrayList<String>()
    var ipv6: List<String> = ArrayList<String>()
    var status: VirtualMachineStatus = VirtualMachineStatus.offline
    var specification: VirtualMachineSpecification = VirtualMachineSpecification()

    //TODO: Add backup related fileds
    //var backup





}