package com.example.parking.controller;

import com.example.parking.model.User;
import com.example.parking.service.ShootingAnalysisService;
import com.example.parking.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shooting-analysis")
@RequiredArgsConstructor
public class ShootingAnalysisController {

    private final ShootingAnalysisService shootingAnalysisService;

    @GetMapping("/team/{teamId}")
    public ResponseEntity<ShootingAnalysisService.TeamShootingEfficiency> getTeamShootingEfficiency(
            @PathVariable Long teamId) {
        return ResponseEntity.ok(shootingAnalysisService.calculateTeamShootingEfficiency(teamId));
    }

    @GetMapping("/player/{playerId}")
    public ResponseEntity<ShootingAnalysisService.PlayerShootingEfficiency> getPlayerShootingEfficiency(
            @PathVariable Long playerId) {
        return ResponseEntity.ok(shootingAnalysisService.calculatePlayerShootingEfficiency(playerId));
    }

    @GetMapping("/compare/{team1Id}/{team2Id}")
    public ResponseEntity<ShootingAnalysisService.TeamShootingComparison> compareTeamsShooting(
            @PathVariable Long team1Id, @PathVariable Long team2Id) {
        return ResponseEntity.ok(shootingAnalysisService.compareTeamsShooting(team1Id, team2Id));
    }
}