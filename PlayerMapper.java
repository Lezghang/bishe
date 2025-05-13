package com.example.parking.mapper;

import com.example.parking.model.Player;
import com.example.parking.model.Team;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PlayerMapper {
    @Insert("INSERT INTO player (name, position, birth_date, nationality, height, weight, team_id) " +
            "VALUES (#{name}, #{position}, #{birthDate}, #{nationality}, #{height}, #{weight}, #{teamId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertPlayer(Player player);

    @Select("SELECT * FROM player WHERE id = #{id}")
    @Results({
            @Result(property = "team", column = "team_id",
                    one = @One(select = "com.example.football.mapper.TeamMapper.selectTeamById"))
    })
    Player selectPlayerById(Long id);

    @Select("SELECT * FROM player WHERE team_id = #{teamId}")
    List<Player> selectPlayersByTeamId(Long teamId);

    @Select("SELECT * FROM player WHERE name LIKE CONCAT('%', #{name}, '%')")
    List<Player> selectPlayersByName(String name);
}