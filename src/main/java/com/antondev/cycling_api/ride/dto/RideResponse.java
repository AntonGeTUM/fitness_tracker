package com.antondev.cycling_api.ride.dto;

import com.antondev.cycling_api.ride.RideType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RideResponse {

    private Long id;
    private String title;
    private LocalDate date;
    private Double distanceKm;
    private Integer durationSeconds;
    private RideType rideType;
    private Integer elevationMeters;
    private Integer avgWatts;
    private Integer avgHeartRate;
    private Double avgSpeedKmh;
    private Double wattsPerKg;
    private LocalDateTime createdAt;
}