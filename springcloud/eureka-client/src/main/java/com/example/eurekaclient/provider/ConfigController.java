package com.example.eurekaclient.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cfg")
public class ConfigController {
    @Value("${person.name}")
    String person;


    @RequestMapping(value = "/person")
    public String person(){
        return person;
    }
}
