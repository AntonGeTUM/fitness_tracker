package com.antondev.cycling_api.ride;

import com.antondev.cycling_api.ride.dto.RecordsResponse;
import com.antondev.cycling_api.ride.dto.RideRequest;
import com.antondev.cycling_api.ride.dto.RideResponse;
import com.antondev.cycling_api.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RideService {

    private final RideRepository rideRepository;

    public RideResponse createRide(User currentUser, RideRequest request) {
        double avgSpeedKmh = calculateAvgSpeed(request.getDistanceKm(), request.getDurationSeconds());
        Double wattsPerKg = calculateWattsPerKg(request.getAvgWatts(), currentUser.getWeightKg());

        Ride ride = Ride.builder()
                .user(currentUser)
                .title(request.getTitle())
                .date(request.getDate())
                .distanceKm(request.getDistanceKm())
                .durationSeconds(request.getDurationSeconds())
                .elevationMeters(request.getElevationMeters())
                .avgWatts(request.getAvgWatts())
                .avgHeartRate(request.getAvgHeartRate())
                .rideType(request.getRideType())
                .avgSpeedKmh(avgSpeedKmh)
                .wattsPerKg(wattsPerKg)
                .build();

        return toResponse(rideRepository.save(ride));
    }

    public List<RideResponse> getRides(User currentUser, LocalDate from, LocalDate to, Double minDistanceKm, RideType rideType) {
        Specification<Ride> spec = Specification.where(RideSpecification.byUser(currentUser.getId()));

        if (from != null) spec = spec.and(RideSpecification.fromDate(from));
        if (to != null) spec = spec.and(RideSpecification.toDate(to));
        if (minDistanceKm != null) spec = spec.and(RideSpecification.minDistance(minDistanceKm));
        if (rideType != null) spec = spec.and(RideSpecification.byRideType(rideType));

        Sort sort = Sort.by(Sort.Direction.DESC, "date");
        return rideRepository.findAll(spec, sort).stream()
                .map(this::toResponse)
                .toList();
    }

    public RideResponse getRide(User currentUser, Long rideId) {
        Ride ride = findRideForUser(currentUser, rideId);
        return toResponse(ride);
    }

    public void deleteRide(User currentUser, Long rideId) {
        Ride ride = findRideForUser(currentUser, rideId);
        rideRepository.delete(ride);
    }

    public RecordsResponse getRecords(User currentUser, String period) {
        LocalDate from = switch (period) {
            case "season" -> LocalDate.of(LocalDate.now().getYear(), 1, 1);
            case "last90" -> LocalDate.now().minusDays(90);
            default -> LocalDate.of(2000, 1, 1);
        };

        Specification<Ride> spec = Specification
                .where(RideSpecification.byUser(currentUser.getId()))
                .and(RideSpecification.fromDate(from));

        List<Ride> rides = rideRepository.findAll(spec);

        return RecordsResponse.builder()
                .longestDistanceKm(rides.stream().mapToDouble(Ride::getDistanceKm).max().orElse(0))
                .mostElevationMeters(rides.stream().mapToInt(r -> r.getElevationMeters() != null ? r.getElevationMeters() : 0).max().orElse(0))
                .highestAvgWatts(rides.stream().mapToInt(r -> r.getAvgWatts() != null ? r.getAvgWatts() : 0).max().orElse(0))
                .highestAvgSpeedKmh(rides.stream().mapToDouble(Ride::getAvgSpeedKmh).max().orElse(0))
                .build();
    }

    private Ride findRideForUser(User currentUser, Long rideId) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found"));
        if (!ride.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Not your ride");
        }
        return ride;
    }

    private double calculateAvgSpeed(Double distanceKm, Integer durationSeconds) {
        return distanceKm / (durationSeconds / 3600.0);
    }

    private Double calculateWattsPerKg(Integer avgWatts, Double weightKg) {
        if (avgWatts == null || weightKg == null) return null;
        return avgWatts / weightKg;
    }

    private RideResponse toResponse(Ride ride) {
        return RideResponse.builder()
                .id(ride.getId())
                .title(ride.getTitle())
                .date(ride.getDate())
                .distanceKm(ride.getDistanceKm())
                .durationSeconds(ride.getDurationSeconds())
                .elevationMeters(ride.getElevationMeters())
                .avgWatts(ride.getAvgWatts())
                .avgHeartRate(ride.getAvgHeartRate())
                .rideType(ride.getRideType())
                .avgSpeedKmh(ride.getAvgSpeedKmh())
                .wattsPerKg(ride.getWattsPerKg())
                .createdAt(ride.getCreatedAt())
                .build();
    }
}
