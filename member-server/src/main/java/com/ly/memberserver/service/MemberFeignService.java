package com.ly.memberserver.service;

import org.springframework.stereotype.Component;

@Component
public class MemberFeignService implements OrderFeign {

    @Override
    public String orderTest() {
        System.out.println("FeignClient fallback 熔断 ...");
        return "FeignClient fallback 熔断 ...";
    }
}
