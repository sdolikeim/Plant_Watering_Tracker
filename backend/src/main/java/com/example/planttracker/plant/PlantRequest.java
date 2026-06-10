package com.example.planttracker.plant;

import java.time.LocalDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PlantRequest(
    
    @NotBlank(message = "식물 이름은 필수입니다.")
    String name,

    @NotNull(message = "물주기 주기는 필수입니다.")
    @Min(value = 1, message = "물주기 주기는 1일 이상이어야 합니다.")
    Integer wateringIntervalDays,

    @NotNull(message = "마지막 물 준 날짜는 필수입니다.")
    LocalDate lastWateredDate,

    String note
    ) {
}
