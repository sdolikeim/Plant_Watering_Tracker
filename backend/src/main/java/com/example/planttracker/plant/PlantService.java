package com.example.planttracker.plant;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlantService {

    private final PlantRepository plantRepository;

    public List<PlantResponse> getPlants(PlantStatus status) {
        return plantRepository.findAll()
                .stream()
                .map(PlantResponse::from)
                .filter(response -> status == null || response.status()==status)
                .toList();
    }

    public PlantResponse getPlant(Long id) {
        Plant plant = findPlant(id);
        return PlantResponse.from(plant);
    }

    @Transactional
    public PlantResponse createPlant(PlantRequest request) {
        Plant plant = Plant.builder()
                .name(request.name())
                .wateringIntervalDays(request.wateringIntervalDays())
                .lastWateredDate(request.lastWateredDate())
                .note(request.note())
                .build();

        Plant savedPlant = plantRepository.save(plant);

        return PlantResponse.from(savedPlant);
    }

    @Transactional
    public PlantResponse updatePlant(Long id, PlantRequest request) {
        Plant plant = findPlant(id);

        plant.setName(request.name());
        plant.setWateringIntervalDays(request.wateringIntervalDays());
        plant.setLastWateredDate(request.lastWateredDate());
        plant.setNote(request.note());

        return PlantResponse.from(plant);
    }

    @Transactional
    public void deletePlant(Long id) {
        Plant plant = findPlant(id);
        plantRepository.delete(plant);
    }

    @Transactional
    public PlantResponse waterPlant(Long id) {
        Plant plant = findPlant(id);
        plant.setLastWateredDate(LocalDate.now());

        return PlantResponse.from(plant);
    }

    private Plant findPlant(Long id) {
        return plantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("식물을 찾을 수 없습니다. id=" + id));
    }
}