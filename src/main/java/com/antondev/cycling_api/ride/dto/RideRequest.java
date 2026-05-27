package com.antondev.cycling_api.ride.dto;

import com.antondev.cycling_api.ride.RideType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RideRequest {

    @NotBlank
    private String title;

    @NotNull
    private LocalDate date;

    @NotNull
    @Positive
    private Double distanceKm;

    @NotNull
    @Positive
    private Integer durationSeconds;

    @NotNull
    private RideType rideType;

    private Integer elevationMeters;
    private Integer avgWatts;
    private Integer avgHeartRate;
}
