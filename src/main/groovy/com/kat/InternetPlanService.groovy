package com.kat

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class InternetPlanService {

    @GetMapping (value="/ping")
    String ping() {
        return "{ 'msg': 'ISP plan endpoint is running.' }"
    }
}
