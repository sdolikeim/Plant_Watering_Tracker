package com.example.planttracker.plant;

import java.time.LocalDate;

public record PlantResponse(
    
    Long id,
    String name,
    Integer wateringIntervalDays,
    LocalDate lastWateredDate,
    LocalDate nextWateringDate,
    PlantStatus status,
    String note

    ) {

        public static PlantResponse from(Plant plant){
            return new PlantResponse(
                plant.getId(),
                plant.getName(),
                plant.getWateringIntervalDays(),
                plant.getLastWateredDate(),
                plant.getNextWateringDate(),
                plant.getStatus(),
                plant.getNote()
            );
        }
}
