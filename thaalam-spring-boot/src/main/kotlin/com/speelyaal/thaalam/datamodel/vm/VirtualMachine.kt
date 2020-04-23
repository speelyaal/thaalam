package com.speelyaal.thaalam.datamodel.vm


import com.speelyaal.thaalam.datamodel.ThaalamResource
import java.util.*
import kotlin.collections.ArrayList

class VirtualMachine : ThaalamResource()  {


    var osImageIdentifier: String =""
    var vmTypeIdentifier: String =""
    var vmRegionIdentifier: String = ""
    var group: String =""
    var sshKeys: List<SSHKey> = ArrayList<SSHKey>()
    var ipv4: List<String> = ArrayList<String>()
    var ipv6: List<String> = ArrayList<String>()
    var status: VirtualMachineStatus = VirtualMachineStatus.offline
    var specification: VirtualMachineSpecification = VirtualMachineSpecification()

    //TODO: Add backup related fileds
    //var backup



}