package com.tourguide.gps.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GpsController {

    @GetMapping("/")
    public String home() {
        return "Gps Values are here !!";
    }
}
