package com.speelyaal.thaalam.controllers.common

import com.speelyaal.thaalam.controllers.ThaalamResourceController
import com.speelyaal.thaalam.transformers.utils.RestHelper
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("regions2")
class RegionController(restHelper: RestHelper):
                       ThaalamResourceController(restHelper) {



}