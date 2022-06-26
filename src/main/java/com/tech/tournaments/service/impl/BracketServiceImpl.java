package com.tech.tournaments.service.impl;

import com.tech.tournaments.model.*;
import com.tech.tournaments.model.dto.MatchDto;
import com.tech.tournaments.repository.BracketRepository;
import com.tech.tournaments.repository.MatchRepository;
import com.tech.tournaments.repository.TeamRelationshipRepository;
import com.tech.tournaments.service.BracketService;
import com.tech.tournaments.service.MatchService;
import com.tech.tournaments.service.TournamentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.tech.tournaments.model.enums.TournamentType.ROUND_ROBIN;
import static com.tech.tournaments.model.enums.TournamentType.SINGLE_ELIMINATION;

@Service
@Slf4j
public class BracketServiceImpl implements BracketService {

    private final TournamentService tournamentService;
    private final MatchService matchService;
    private final BracketRepository bracketRepository;
    private final MatchRepository matchRepository;

    @Autowired
    public BracketServiceImpl(@Lazy TournamentService tournamentService,
                              MatchService matchService,
                              BracketRepository bracketRepository,
                              MatchRepository matchRepository) {
        this.tournamentService = tournamentService;
        this.matchService = matchService;
        this.bracketRepository = bracketRepository;
        this.matchRepository = matchRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Match> getBracketMatchesById(UUID id) {
        LOG.info("Get bracket matches by bracket id: {}", id);
        return matchRepository.findByBracketId(id);
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
     * {@inheritDoc}
     */
    @Override
    public boolean generateNewRoundForTournament(Tournament tournament) {
        var bracket = tournament.getBracket();
        int round = bracket.getMatches().stream().map(Match::getRound).max(Integer::compareTo).orElseThrow();
        var winners = bracket.getMatches().stream()
                .filter(m -> (m.getRound() == round)).map(Match::getResult)
                .map(MatchResult::getWinnerId).collect(Collectors.toList());
        if (bracket.getTournamentType() == ROUND_ROBIN || winners.size() == 1) {
            return false;
        }
        if (bracket.getTournamentType() == SINGLE_ELIMINATION && winners.size() % 2 != 0) {
            throw new RuntimeException("Incorrect number of winners");
        }

        // create new round for SINGLE_ELIMINATION
        for (int i = 0; i < winners.size() - 1; i += 2) {
                    var matchDto = MatchDto.builder()
                            .round(round + 1)
                            .team1Id(winners.get(i))
                            .team2Id(winners.get(i + 1))
                            .bracket(bracket)
                            .build();
            this.matchService.createNewMatch(matchDto);
        }
        return true;
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
        var teams = this.tournamentService.getTournamentTeamsById(tournament.getId());
        switch (bracket.getTournamentType()) {
            case SINGLE_ELIMINATION:
                matchDto.setRound(1);
                for (int i = 0; i < teams.size() - 1; i += 2) {
                    matchDto.setTeam1Id(teams.get(i));
                    matchDto.setTeam2Id(teams.get(i + 1));
                    this.matchService.createNewMatch(matchDto);
                }
                break;
            case ROUND_ROBIN:
                int round = 1;
                for (int i = 0; i < teams.size() - 1; i++) {
                    for (int j = i + 1; j < teams.size(); j++) {
                        matchDto.setTeam1Id(teams.get(i));
                        matchDto.setTeam2Id(teams.get(j));
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