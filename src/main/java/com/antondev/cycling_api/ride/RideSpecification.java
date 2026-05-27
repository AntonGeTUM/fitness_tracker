package com.antondev.cycling_api.ride;

import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class RideSpecification {

    public static Specification<Ride> byUser(Long userId) {
        return (root, query, cb) -> cb.equal(root.get("user").get("id"), userId);
    }

    public static Specification<Ride> fromDate(LocalDate from) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("date"), from);
    }

    public static Specification<Ride> toDate(LocalDate to) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("date"), to);
    }

    public static Specification<Ride> minDistance(Double minDistanceKm) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("distanceKm"), minDistanceKm);
    }

    public static Specification<Ride> byRideType(RideType type) {
        return (root, query, cb) -> cb.equal(root.get("rideType"), type);
    }
}

