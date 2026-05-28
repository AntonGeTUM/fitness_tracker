package com.antondev.cycling_api.ride.gpx;

import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Component
public class GpxCalculator {

    private static final double EARTH_RADIUS_KM = 6371.0;

    public double calculateDistance(List<GpxTrackPoint> points) {
        double total = 0;
        for (int i = 1; i < points.size(); i++) {
            total += haversine(points.get(i - 1), points.get(i));
        }
        return total;
    }

    // total elevation gain -> only positive differences
    public int calculateElevationGain(List<GpxTrackPoint> points) {
        double gain = 0;
        for (int i = 1; i < points.size(); i++) {
            Double prev = points.get(i - 1).getElevation();
            Double curr = points.get(i).getElevation();
            if (prev != null && curr != null && curr > prev) {
                gain += curr - prev;
            }
        }
        return (int) gain;
    }

    public int calculateDuration(List<GpxTrackPoint> points) {
        if (points.size() < 2) return 0;
        Instant start = Instant.parse(points.get(0).getTime());
        Instant end = Instant.parse(points.get(points.size() - 1).getTime());
        return (int) Duration.between(start, end).getSeconds();
    }

    // can be missing from the GPX
    public Integer calculateAvgPower(List<GpxTrackPoint> points) {
        return points.stream()
                .map(GpxTrackPoint::getPower)
                .filter(Objects::nonNull)
                .mapToInt(Integer::intValue)
                .average()
                .stream()
                .mapToObj(d -> (int) d)
                .findFirst()
                .orElse(null);
    }

    // can be missing from the GPX
    public Integer calculateAvgHeartRate(List<GpxTrackPoint> points) {
        return points.stream()
                .map(GpxTrackPoint::getHeartRate)
                .filter(Objects::nonNull)
                .mapToInt(Integer::intValue)
                .average()
                .stream()
                .mapToObj(d -> (int) d)
                .findFirst()
                .orElse(null);
    }

    // https://de.wikipedia.org/wiki/Haversine-Formel
    // https://www.baeldung.com/java-find-distance-between-points
    private double haversine(GpxTrackPoint a, GpxTrackPoint b) {
        double dLat = Math.toRadians(b.getLat() - a.getLat());
        double dLon = Math.toRadians(b.getLon() - a.getLon());
        double lat1 = Math.toRadians(a.getLat());
        double lat2 = Math.toRadians(b.getLat());

        double x = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(lat1) * Math.cos(lat2) * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        return 2 * EARTH_RADIUS_KM * Math.asin(Math.sqrt(x));
    }
}