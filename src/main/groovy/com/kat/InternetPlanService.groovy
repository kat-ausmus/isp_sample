package com.kat

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/isp")
class InternetPlanService {

    @RequestMapping(value="/ping", method=RequestMethod.GET)
    public String ping() {
        return "{ 'msg': 'ISP plan endpoint is running.' }"
    }
}
