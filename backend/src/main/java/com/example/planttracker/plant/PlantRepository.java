package com.example.planttracker.plant;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantRepository  extends JpaRepository<Plant, Long>{
    
}
