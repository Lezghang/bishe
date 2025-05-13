package com.example.parking.service;

import com.example.parking.mapper.MatchMapper;
import com.example.parking.mapper.PlayerMapper;
import com.example.parking.mapper.PlayerMatchStatsMapper;
import com.example.parking.mapper.TeamMapper;
import com.example.parking.model.Match;
import com.example.parking.model.Player;
import com.example.parking.model.PlayerMatchStats;
import com.example.parking.model.Team;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShootingAnalysisService {

    private final MatchMapper matchMapper;
    private final TeamMapper teamMapper;
    private final PlayerMapper playerMapper;
    private final PlayerMatchStatsMapper playerMatchStatsMapper;

    /**
     * 计算球队的射门效率
     */
    public TeamShootingEfficiency calculateTeamShootingEfficiency(Long teamId) {
        Team team = teamMapper.selectTeamById(teamId);
        if (team == null) {
            throw new EntityNotFoundException("Team not found");
        }

        List<Match> matches = matchMapper.selectMatchesByTeamId(teamId);

        int totalShots = 0;
        int totalShotsOnTarget = 0;
        int totalGoals = 0;

        for (Match match : matches) {
            if (match.getTeam1Id().equals(teamId)) {
                totalShots += match.getTeam1Shots();
                totalShotsOnTarget += match.getTeam1ShotsOnTarget();
                totalGoals += match.getTeam1Goals();
            } else {
                totalShots += match.getTeam2Shots();
                totalShotsOnTarget += match.getTeam2ShotsOnTarget();
                totalGoals += match.getTeam2Goals();
            }
        }

        if (totalShots == 0) {
            return new TeamShootingEfficiency(team, 0, 0, 0);
        }

        double shotAccuracy = (double) totalShotsOnTarget / totalShots * 100;
        double conversionRate = (double) totalGoals / totalShots * 100;
        double onTargetConversionRate = totalShotsOnTarget == 0 ? 0 :
                (double) totalGoals / totalShotsOnTarget * 100;

        return new TeamShootingEfficiency(team, shotAccuracy, conversionRate, onTargetConversionRate);
    }

    /**
     * 计算球员的射门效率
     */
    public PlayerShootingEfficiency calculatePlayerShootingEfficiency(Long playerId) {
        Player player = playerMapper.selectPlayerById(playerId);
        if (player == null) {
            throw new EntityNotFoundException("Player not found");
        }

        List<PlayerMatchStats> stats = playerMatchStatsMapper.selectStatsByPlayerId(playerId);

        int totalShots = 0;
        int totalShotsOnTarget = 0;
        int totalGoals = 0;
        int totalMinutes = 0;

        for (PlayerMatchStats stat : stats) {
            totalShots += stat.getShots();
            totalShotsOnTarget += stat.getShotsOnTarget();
            totalGoals += stat.getGoals();
            totalMinutes += stat.getMinutesPlayed();
        }

        if (totalShots == 0) {
            return new PlayerShootingEfficiency(player, 0, 0, 0, 0);
        }

        double shotAccuracy = (double) totalShotsOnTarget / totalShots * 100;
        double conversionRate = (double) totalGoals / totalShots * 100;
        double onTargetConversionRate = totalShotsOnTarget == 0 ? 0 :
                (double) totalGoals / totalShotsOnTarget * 100;
        double goalsPer90 = totalMinutes == 0 ? 0 : (double) totalGoals / totalMinutes * 90;

        return new PlayerShootingEfficiency(player, shotAccuracy, conversionRate, onTargetConversionRate, goalsPer90);
    }

    /**
     * 比较两个球队的射门效率
     */
    public TeamShootingComparison compareTeamsShooting(Long team1Id, Long team2Id) {
        TeamShootingEfficiency team1Efficiency = calculateTeamShootingEfficiency(team1Id);
        TeamShootingEfficiency team2Efficiency = calculateTeamShootingEfficiency(team2Id);

        return new TeamShootingComparison(team1Efficiency, team2Efficiency);
    }

    // DTO类
    @Data
    @AllArgsConstructor
    public static class TeamShootingEfficiency {
        private Team team;
        private double shotAccuracy; // 射门准确率(射正/射门)
        private double conversionRate; // 转化率(进球/射门)
        private double onTargetConversionRate; // 射正转化率(进球/射正)
    }

    @Data
    @AllArgsConstructor
    public static class PlayerShootingEfficiency {
        private Player player;
        private double shotAccuracy;
        private double conversionRate;
        private double onTargetConversionRate;
        private double goalsPer90;
    }

    @Data
    @AllArgsConstructor
    public static class TeamShootingComparison {
        private TeamShootingEfficiency team1;
        private TeamShootingEfficiency team2;
    }
}