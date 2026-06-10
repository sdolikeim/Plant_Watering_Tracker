package com.example.planttracker.health;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthContoller {
    
    @GetMapping("/api/health")
    public String health(){
        return "Plant Watering Tracker API is running";
    }

}
