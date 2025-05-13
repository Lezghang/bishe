package com.example.parking.mapper;

import com.example.parking.model.Player;
import com.example.parking.model.PlayerMatchStats;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PlayerMatchStatsMapper {
    @Insert("INSERT INTO player_match_stats (player_id, match_id, team_id, goals, assists, " +
            "shots, shots_on_target, passes, passes_completed, dribbles, dribbles_successful, " +
            "tackles, interceptions, fouls_committed, fouls_suffered, minutes_played) " +
            "VALUES (#{playerId}, #{matchId}, #{teamId}, #{goals}, #{assists}, " +
            "#{shots}, #{shotsOnTarget}, #{passes}, #{passesCompleted}, #{dribbles}, #{dribblesSuccessful}, " +
            "#{tackles}, #{interceptions}, #{foulsCommitted}, #{foulsSuffered}, #{minutesPlayed})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertPlayerMatchStats(PlayerMatchStats stats);

    @Select("SELECT * FROM player_match_stats WHERE id = #{id}")
    @Results({
            @Result(property = "player", column = "player_id",
                    one = @One(select = "com.example.football.mapper.PlayerMapper.selectPlayerById")),
            @Result(property = "match", column = "match_id",
                    one = @One(select = "com.example.football.mapper.MatchMapper.selectMatchById")),
            @Result(property = "team", column = "team_id",
                    one = @One(select = "com.example.football.mapper.TeamMapper.selectTeamById"))
    })
    PlayerMatchStats selectPlayerMatchStatsById(Long id);

    @Select("SELECT * FROM player_match_stats WHERE player_id = #{playerId}")
    @Results({
            @Result(property = "match", column = "match_id",
                    one = @One(select = "com.example.football.mapper.MatchMapper.selectMatchById")),
            @Result(property = "team", column = "team_id",
                    one = @One(select = "com.example.football.mapper.TeamMapper.selectTeamById"))
    })
    List<PlayerMatchStats> selectStatsByPlayerId(Long playerId);

    @Select("SELECT * FROM player_match_stats WHERE match_id = #{matchId}")
    @Results({
            @Result(property = "player", column = "player_id",
                    one = @One(select = "com.example.football.mapper.PlayerMapper.selectPlayerById")),
            @Result(property = "team", column = "team_id",
                    one = @One(select = "com.example.football.mapper.TeamMapper.selectTeamById"))
    })
    List<PlayerMatchStats> selectStatsByMatchId(Long matchId);

    @Select("SELECT * FROM player_match_stats WHERE team_id = #{teamId}")
    @Results({
            @Result(property = "player", column = "player_id",
                    one = @One(select = "com.example.football.mapper.PlayerMapper.selectPlayerById")),
            @Result(property = "match", column = "match_id",
                    one = @One(select = "com.example.football.mapper.MatchMapper.selectMatchById"))
    })
    List<PlayerMatchStats> selectStatsByTeamId(Long teamId);
}