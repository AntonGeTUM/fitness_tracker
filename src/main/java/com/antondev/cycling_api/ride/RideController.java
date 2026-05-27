package com.antondev.cycling_api.ride;

import com.antondev.cycling_api.ride.dto.RecordsResponse;
import com.antondev.cycling_api.ride.dto.RideRequest;
import com.antondev.cycling_api.ride.dto.RideResponse;
import com.antondev.cycling_api.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/rides")
@RequiredArgsConstructor
public class RideController {

    private final RideService rideService;

    @PostMapping
    public ResponseEntity<RideResponse> createRide(
            @AuthenticationPrincipal User currentUser,
            @Valid @RequestBody RideRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(rideService.createRide(currentUser, request));
    }

    @GetMapping
    public ResponseEntity<List<RideResponse>> getRides(
            @AuthenticationPrincipal User currentUser,
            @RequestParam(required = false) LocalDate from,
            @RequestParam(required = false) LocalDate to,
            @RequestParam(required = false) Double minDistanceKm,
            @RequestParam(required = false) RideType rideType) {
        return ResponseEntity.ok(rideService.getRides(currentUser, from, to, minDistanceKm, rideType));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RideResponse> getRide(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long id) {
        return ResponseEntity.ok(rideService.getRide(currentUser, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRide(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long id) {
        rideService.deleteRide(currentUser, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/records")
    public ResponseEntity<RecordsResponse> getRecords(
            @AuthenticationPrincipal User currentUser,
            @RequestParam(defaultValue = "alltime") String period) {
        return ResponseEntity.ok(rideService.getRecords(currentUser, period));
    }
}
