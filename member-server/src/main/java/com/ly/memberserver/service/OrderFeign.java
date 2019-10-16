package com.ly.memberserver.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@FeignClient(name = "order-server", fallback = MemberFeignService.class)
public interface OrderFeign {

    @RequestMapping("/orderTest")
    String orderTest();
}
