package com.tech.tournaments.service;

import com.tech.tournaments.model.dto.TournamentDto;
import com.tech.tournaments.model.enums.TournamentStatus;
import com.tech.tournaments.model.enums.TournamentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@Transactional
public class TournamentServiceTests {

    @Autowired
    TournamentService tournamentService;

    @Test
    public void tournamentCreationTest() {
        var tournament = this.tournamentService.createNewTournament(TournamentDto.builder()
                .creatorId(UUID.randomUUID())
                .startDateTime(LocalDateTime.of(2022, 10, 10, 10, 10, 10))
                .tournamentType(TournamentType.SINGLE_ELIMINATION)
                .build());

        var teams = List.of(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());
        teams.forEach(id -> this.tournamentService.addTeamInTournament(tournament.getId(), id));

        var tournamentFromDb = this.tournamentService.getTournamentById(tournament.getId());
        var tournamentTeamsFromDb = this.tournamentService.getTournamentTeamsById(tournament.getId());

        assert tournamentFromDb.getId().equals(tournament.getId());
        assert tournamentTeamsFromDb.equals(teams);
    }

    @Test
    public void tournamentStartAndFinishTest() {
        var tournament = this.tournamentService.createNewTournament(TournamentDto.builder()
                .creatorId(UUID.randomUUID())
                .startDateTime(LocalDateTime.of(2022, 10, 10, 10, 10, 10))
                .tournamentType(TournamentType.SINGLE_ELIMINATION)
                .build());

        var teams = List.of(UUID.randomUUID(), UUID.randomUUID());
        teams.forEach(id -> this.tournamentService.addTeamInTournament(tournament.getId(), id));

        this.tournamentService.startTournament(tournament.getId());
        assert this.tournamentService.getTournamentById(tournament.getId()).getTournamentStatus().equals(TournamentStatus.ONGOING);
    }
}
