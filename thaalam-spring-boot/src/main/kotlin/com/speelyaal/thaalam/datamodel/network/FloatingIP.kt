package com.speelyaal.thaalam.datamodel.network

import com.speelyaal.thaalam.datamodel.ThaalamResource

class FloatingIP: ThaalamResource() {
    var ip: String = ""
    var ipType: IPType = IPType.ipv4
    var ipLocationIdentifier: String = ""
    var vmVendorReference: String = ""
}