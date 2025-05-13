package com.example.parking.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PlayerMatchStats {
    private Long id;
    private Long playerId;
    private Long matchId;
    private Long teamId;
    private Integer goals;
    private Integer assists;
    private Integer shots;
    private Integer shotsOnTarget;
    private Integer passes;
    private Integer passesCompleted;
    private Integer dribbles;
    private Integer dribblesSuccessful;
    private Integer tackles;
    private Integer interceptions;
    private Integer foulsCommitted;
    private Integer foulsSuffered;
    private Integer minutesPlayed;

    // 关联对象(非数据库字段)
    private Player player;
    private Match match;
    private Team team;
}