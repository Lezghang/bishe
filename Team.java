package com.example.parking.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Team {
    private Long id;
    private String name;
    private String coach;
    private Integer foundedYear;
    private String stadium;

    // 关联对象(非数据库字段)
    private List<Player> players;
}