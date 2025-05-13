package com.example.parking.mapper;

import com.example.parking.model.Match;
import com.example.parking.model.Team;
import lombok.Data;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface MatchMapper {
    @Insert("INSERT INTO `match` (team1_id, team2_id, team1_goals, team2_goals, " +
            "team1_possession, team2_possession, team1_shots, team2_shots, " +
            "team1_shots_on_target, team2_shots_on_target, team1_fouls, team2_fouls, " +
            "team1_corners, team2_corners, match_date, venue) " +
            "VALUES (#{team1Id}, #{team2Id}, #{team1Goals}, #{team2Goals}, " +
            "#{team1Possession}, #{team2Possession}, #{team1Shots}, #{team2Shots}, " +
            "#{team1ShotsOnTarget}, #{team2ShotsOnTarget}, #{team1Fouls}, #{team2Fouls}, " +
            "#{team1Corners}, #{team2Corners}, #{matchDate}, #{venue})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertMatch(Match match);

    @Select("SELECT * FROM `match` WHERE id = #{id}")
    @Results({
            @Result(property = "team1", column = "team1_id",
                    one = @One(select = "com.example.football.mapper.TeamMapper.selectTeamById")),
            @Result(property = "team2", column = "team2_id",
                    one = @One(select = "com.example.football.mapper.TeamMapper.selectTeamById"))
    })
    Match selectMatchById(Long id);

    @Select("SELECT * FROM `match` WHERE team1_id = #{teamId} OR team2_id = #{teamId}")
    @Results({
            @Result(property = "team1", column = "team1_id",
                    one = @One(select = "com.example.football.mapper.TeamMapper.selectTeamById")),
            @Result(property = "team2", column = "team2_id",
                    one = @One(select = "com.example.football.mapper.TeamMapper.selectTeamById"))
    })
    List<Match> selectMatchesByTeamId(Long teamId);
}