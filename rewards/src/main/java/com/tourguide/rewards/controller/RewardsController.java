package com.tourguide.rewards.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RewardsController {

    @GetMapping("/")
    public String home() {
        return "Rewards are here !!";
    }
}
