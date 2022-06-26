package com.tech.tournaments.service;

import com.tech.tournaments.model.dto.TournamentDto;
import com.tech.tournaments.model.enums.TournamentType;
import com.tech.tournaments.service.BracketService;
import com.tech.tournaments.service.MatchService;
import com.tech.tournaments.service.TournamentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MatchServiceTests {

    @Autowired
    private MatchService matchService;
    @Autowired
    private BracketService bracketService;
    @Autowired
    private TournamentService tournamentService;

    @Test
    public void contextLoads() throws Exception {
        assertThat(matchService).isNotNull();
    }

    @Test
    public void matchCreationTest() throws Exception {
        var tournament = this.tournamentService.createNewTournament(
                TournamentDto.builder()
                        .creatorId(UUID.randomUUID())
                        .tournamentType(TournamentType.SINGLE_ELIMINATION)
                        .build()
        );
        assertThat(tournament).isNotNull();
        var teams = List.of(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());
        this.tournamentService.addTeamInTournament(tournament.getId(), teams.get(0));
        this.tournamentService.addTeamInTournament(tournament.getId(), teams.get(1));
        this.tournamentService.addTeamInTournament(tournament.getId(), teams.get(2));
        this.tournamentService.addTeamInTournament(tournament.getId(), teams.get(3));
        var bracket = this.bracketService.generateBracketForTournament(tournament);
        assertThat(bracket).isNotNull();
        assertThat(this.bracketService.getBracketMatchesById(bracket.getId()).size()).isEqualTo(2);
        assertThat(this.bracketService.getBracketMatchesById(bracket.getId()).get(0).getTeam1Id()).isIn(teams);
        assertThat(this.bracketService.getBracketMatchesById(bracket.getId()).get(1).getTeam1Id()).isIn(teams);
    }
}
