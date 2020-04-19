package com.speelyaal.thaalam.datamodel



data class Region(var id: String) {

     var vendorReference: String = ""
    var description: String = ""
    var countryCode: String =""
    var city: String =""
    var additionalProperties: HashMap<String, String>  = HashMap()
    



}