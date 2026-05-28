package com.antondev.cycling_api.ride;

import com.antondev.cycling_api.user.User;
import com.antondev.cycling_api.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;

@Component
@RequiredArgsConstructor
@Slf4j
public class WeeklyStatsScheduler {

    private final UserRepository userRepository;
    private final RideRepository rideRepository;
    private final WeeklyStatsRepository weeklyStatsRepository;

    /***
     Calculate weekly ride stats for all users.
     ***/
    @Scheduled(cron = "0 0 3 * * MON")
    public void calculateWeeklyStats() {
        log.info("Calculating weekly stats for all users");

        LocalDate lastWeekStart = LocalDate.now().minusWeeks(1);
        LocalDate lastWeekEnd = lastWeekStart.plusDays(6);

        int year = lastWeekStart.getYear();
        int week = lastWeekStart.get(WeekFields.of(Locale.GERMANY).weekOfWeekBasedYear());

        List<User> users = userRepository.findAll();

        for (User user : users) {
            calculateForUser(user, year, week, lastWeekStart, lastWeekEnd);
        }

        log.info("Weekly stats calculation complete");
    }

    @Async
    public void calculateForUser(User user, int year, int week, LocalDate from, LocalDate to) {
        Specification<Ride> spec = Specification
                .where(RideSpecification.byUser(user.getId()))
                .and(RideSpecification.fromDate(from))
                .and(RideSpecification.toDate(to));

        List<Ride> rides = rideRepository.findAll(spec);

        WeeklyStats stats = weeklyStatsRepository
                .findByUserAndYearAndWeek(user, year, week)
                .orElse(WeeklyStats.builder().user(user).year(year).week(week).build());

        stats.setRideCount(rides.size());
        stats.setTotalDistanceKm(rides.stream().mapToDouble(Ride::getDistanceKm).sum());
        stats.setTotalElevationMeters(rides.stream().mapToInt(r -> r.getElevationMeters() != null ? r.getElevationMeters() : 0).sum());
        stats.setTotalDurationSeconds(rides.stream().mapToInt(Ride::getDurationSeconds).sum());
        stats.setCalculatedAt(LocalDateTime.now());

        weeklyStatsRepository.save(stats);
    }
}
