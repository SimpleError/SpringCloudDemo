package com.ly.memberserver.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Ordercontroller {


    @RequestMapping("orderTest")
    public String orderTest() {
        return "this is orderTest";
    }
}
