package com.antondev.cycling_api.ride.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeeklyStatsResponse {
    private int year;
    private int week;
    private Double totalDistanceKm;
    private Integer totalElevationMeters;
    private Integer totalDurationSeconds;
    private Integer rideCount;
    private LocalDateTime calculatedAt;
}
