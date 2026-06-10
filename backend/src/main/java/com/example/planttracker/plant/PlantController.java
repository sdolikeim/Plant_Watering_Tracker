package com.example.planttracker.plant;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/plants")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173","http://localhost:5174"})
public class PlantController {

    private final PlantService plantService;

    @GetMapping
    public List<PlantResponse> getPlants(
       @RequestParam(required = false) PlantStatus status
       ){
        return plantService.getPlants(status);
    }

    @GetMapping("/{id}")
    public PlantResponse getPlant(@PathVariable Long id){
        return plantService.getPlant(id);
    }

    @PostMapping
    public PlantResponse createPlant(@Valid @RequestBody PlantRequest request) {
        return plantService.createPlant(request);
    }

    @PutMapping("/{id}")
    public PlantResponse updatePlant(
        @PathVariable Long id,
        @Valid @RequestBody PlantRequest request
    ){
        return plantService.updatePlant(id, request);
    }

    @DeleteMapping("/{id}")
    public void deletePlant(@PathVariable Long id){
        plantService.deletePlant(id);
    }

    @PatchMapping("/{id}/water")
    public PlantResponse waterPlant(@PathVariable Long id){
        return plantService.waterPlant(id);
    }

    
}
