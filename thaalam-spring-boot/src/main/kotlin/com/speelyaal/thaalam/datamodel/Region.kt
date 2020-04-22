package com.speelyaal.thaalam.datamodel



class Region : ThaalamResource() {

    var id: String = ""
    var name: String = ""
    var vendorReference: String = ""
    var description: String = ""
    var countryCode: String =""
    var city: String =""
    var additionalProperties: HashMap<String, String>  = HashMap()




}