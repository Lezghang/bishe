package com.example.parking.model;

import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Match {
    private Long id;
    private Long team1Id;
    private Long team2Id;
    private Integer team1Goals;
    private Integer team2Goals;
    private BigDecimal team1Possession;
    private BigDecimal team2Possession;
    private Integer team1Shots;
    private Integer team2Shots;
    private Integer team1ShotsOnTarget;
    private Integer team2ShotsOnTarget;
    private Integer team1Fouls;
    private Integer team2Fouls;
    private Integer team1Corners;
    private Integer team2Corners;
    private LocalDateTime matchDate;
    private String venue;

    // 关联对象(非数据库字段)
    private Team team1;
    private Team team2;
}