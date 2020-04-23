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
    //TODO Random password
    // Linode : Password must contain at least 2 of these 4 character classes: lowercase letters, uppercase letters, numbers, and punctuation
    var rootPassword: String= "asA23@_dfasdfasdfasdfasdasdf"

    //TODO: Add backup related fileds
    //var backup



}