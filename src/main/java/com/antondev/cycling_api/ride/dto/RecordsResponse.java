package com.antondev.cycling_api.ride.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecordsResponse {

    private Double longestDistanceKm;
    private Integer mostElevationMeters;
    private Integer highestAvgWatts;
    private Double highestAvgSpeedKmh;
}
