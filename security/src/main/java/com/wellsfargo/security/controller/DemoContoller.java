package com.wellsfargo.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoContoller {

    @GetMapping
    public String getvalue()
    {
        return "Hello";
    }
}
