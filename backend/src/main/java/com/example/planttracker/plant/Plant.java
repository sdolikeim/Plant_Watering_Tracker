package com.example.planttracker.plant;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Plant {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer wateringIntervalDays;

    @Column(nullable = false)
    private LocalDate lastWateredDate;

    private String note;

    public LocalDate getNextWateringDate(){
        return lastWateredDate.plusDays(wateringIntervalDays);
    }

    public PlantStatus getStatus() {
        LocalDate today = LocalDate.now();
        LocalDate nextWateringDate = getNextWateringDate();

        if(today.isBefore(nextWateringDate)){
            return PlantStatus.OK;
        }

        if(today.isEqual(nextWateringDate)){
            return PlantStatus.DUE_TODAY;
        }

        return PlantStatus.OVERDUE;
    }

}
