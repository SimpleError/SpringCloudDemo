package com.ly.memberserver.controller;


import com.ly.memberserver.service.OrderFeign;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class Membercontroller {

    public static void main(String[] args) {
        int i = 5;
        System.out.println(5/2);
    }

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OrderFeign orderFeign;


    @RequestMapping("memberTest")
    public String memberTest() {
        String str = orderFeign.orderTest();
        System.out.println("feign: " + str);
        return str;
    }

    @HystrixCommand(fallbackMethod = "testError")
    @RequestMapping("memberTest1")
    public String memberTest1() {
        String str = orderFeign.orderTest();
        System.out.println("feign: " + str);
        return str;
    }

    /**
     * 断路
     */
    public String testError() {
        System.out.println("HystrixCommand 熔断...");
        return "HystrixCommand 注解熔断";
    }

    /**
     * HystrixCommand 当方法调用出错时可触发
     * FeignClient fallback无法作用于restTemplate
     * HystrixCommand与FeignClient fallback共存，先调用HystrixCommand,在调用FeignClient，返回结果还是HystrixCommand
     */
    @HystrixCommand(fallbackMethod = "testError")
    @RequestMapping("HystrixCommand")
    public String HystrixCommand() {
        System.out.println("start HystrixCommand ...");
        String str = restTemplate.getForObject("http://order-server/orderTest", String.class);
        System.out.println("restTemplate: " + str);

        str = orderFeign.orderTest();
        System.out.println("feign: " + str);
        return str;
    }
}
