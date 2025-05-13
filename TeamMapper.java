package com.example.parking.mapper;

import com.example.parking.model.Match;
import com.example.parking.model.Player;
import com.example.parking.model.Team;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TeamMapper {
    @Insert("INSERT INTO team (name, coach, founded_year, stadium) " +
            "VALUES (#{name}, #{coach}, #{foundedYear}, #{stadium})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertTeam(Team team);

    @Select("SELECT * FROM team WHERE id = #{id}")
    Team selectTeamById(Long id);

    @Select("SELECT * FROM team WHERE name = #{name}")
    Team selectTeamByName(String name);

    @Select("SELECT * FROM player WHERE team_id = #{teamId}")
    List<Player> selectPlayersByTeamId(Long teamId);

    @Select("SELECT * FROM team")
    List<Team> selectAllTeams();
}