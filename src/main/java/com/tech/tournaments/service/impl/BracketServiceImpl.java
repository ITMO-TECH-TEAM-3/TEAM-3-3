package com.tech.tournaments.service.impl;

import com.tech.tournaments.model.Bracket;
import com.tech.tournaments.model.Tournament;
import com.tech.tournaments.model.dto.MatchDto;
import com.tech.tournaments.repository.BracketRepository;
import com.tech.tournaments.service.BracketService;
import com.tech.tournaments.service.MatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BracketServiceImpl implements BracketService {

    private final MatchService matchService;
    private final BracketRepository bracketRepository;

    @Autowired
    public BracketServiceImpl(MatchService matchService,
                              BracketRepository bracketRepository) {
        this.matchService = matchService;
        this.bracketRepository = bracketRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Bracket generateBracketForTournament(Tournament tournament) {
        LOG.info("Generate bracket for a tournament: {}", tournament);
        var bracket = Bracket.builder()
                .tournament(tournament)
                .tournamentType(tournament.getTournamentType())
                .build();
        bracket = this.bracketRepository.save(bracket);
        createMatches(tournament, bracket);
        return bracket;
    }

    /**
     * Создание матчей для турнирной сетки
     * В случае SINGLE_ELIMINATION - создание только 1 раунда
     *
     * @param tournament турнир
     * @param bracket    сетка
     */
    private void createMatches(Tournament tournament, Bracket bracket) {
        var matchDto = MatchDto.builder()
                .bracket(bracket)
                .build();
        var teams = tournament.getTeams();
        switch (bracket.getTournamentType()) {
            case SINGLE_ELIMINATION:
                matchDto.setRound(1);
                for (int i = 0; i < teams.size() - 1; i += 2) {
                    matchDto.setTeam1Id(teams.get(i).getTeam());
                    matchDto.setTeam2Id(teams.get(i + 1).getTeam());
                    this.matchService.createNewMatch(matchDto);
                }
                break;
            case ROUND_ROBIN:
                int round = 1;
                for (int i = 0; i < teams.size() - 1; i++) {
                    for (int j = i + 1; j < teams.size(); j++) {
                        matchDto.setTeam1Id(teams.get(i).getTeam());
                        matchDto.setTeam2Id(teams.get(j).getTeam());
                        matchDto.setRound(round++);
                        this.matchService.createNewMatch(matchDto);
                    }
                }
                break;
            default:
                break;
        }
    }
}