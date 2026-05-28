package com.antondev.cycling_api.ride;

import com.antondev.cycling_api.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WeeklyStatsRepository extends JpaRepository<WeeklyStats, Long> {

    Optional<WeeklyStats> findByUserAndYearAndWeek(User user, int year, int week);

    List<WeeklyStats> findByUserOrderByYearDescWeekDesc(User user);
}
