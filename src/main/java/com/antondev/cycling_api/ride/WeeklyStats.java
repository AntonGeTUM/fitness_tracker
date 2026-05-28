package com.antondev.cycling_api.ride;

import com.antondev.cycling_api.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "weekly_stats")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeeklyStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false)
    private int week;

    private Double totalDistanceKm;
    private Integer totalElevationMeters;
    private Integer totalDurationSeconds;
    private Integer rideCount;

    @Column(nullable = false)
    private LocalDateTime calculatedAt;
}
