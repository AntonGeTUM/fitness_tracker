package com.antondev.cycling_api.ride;

import com.antondev.cycling_api.ride.dto.RideRequest;
import com.antondev.cycling_api.ride.dto.RideResponse;
import com.antondev.cycling_api.ride.gpx.GpxCalculator;
import com.antondev.cycling_api.ride.gpx.GpxParser;
import com.antondev.cycling_api.user.User;
import com.antondev.cycling_api.user.UserRepository;
import com.antondev.cycling_api.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RideServiceTest {

    @Mock
    private RideRepository rideRepository;

    @InjectMocks
    private RideService rideService;

    @Mock
    private GpxParser gpxParser;

    @Mock
    private GpxCalculator gpxCalculator;

    @Test
    void createRide_successful() {
        User user = new User();
        user.setWeightKg(50.0);
        RideRequest request = new RideRequest("Afternoon ride", LocalDate.of(2026, 7, 2),
                90.0, 12240, RideType.ROAD, 300, 110, 150);

        when(rideRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        double expectedSpeed = 90.0 / (12240 / 3600.0);
        double expectedWattsPerKg = 110 / 50.0;

        RideResponse rideResponse = rideService.createRide(user, request);

        assertThat(rideResponse.getAvgSpeedKmh()).isEqualTo(expectedSpeed);
        assertThat(rideResponse.getWattsPerKg()).isEqualTo(expectedWattsPerKg);
    }

    @Test
    void getRides_successful() {

    }

    @Test
    void getRide_successful() {

    }

    @Test
    void deleteRide_successful() {

    }
}
