package com.example.parking.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class Player {
    private Long id;
    private String name;
    private String position;
    private LocalDate birthDate;
    private String nationality;
    private BigDecimal height;
    private BigDecimal weight;
    private Long teamId;

    // 关联对象(非数据库字段)
    private Team team;
    private List<PlayerMatchStats> matchStats;
}