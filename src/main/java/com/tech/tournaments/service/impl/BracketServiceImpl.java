package com.tech.tournaments.service.impl;

import com.tech.tournaments.model.Bracket;
import com.tech.tournaments.model.Match;
import com.tech.tournaments.model.MatchResult;
import com.tech.tournaments.model.Tournament;
import com.tech.tournaments.model.dto.MatchDto;
import com.tech.tournaments.model.enums.TournamentType;
import com.tech.tournaments.repository.BracketRepository;
import com.tech.tournaments.service.BracketService;
import com.tech.tournaments.service.MatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

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

    /*
     * todo: think of whether its public or private
     */
    @Override
    public Bracket generateNewRoundForTournament(Tournament tournament, Bracket bracket) {
        int round = bracket.getMatches().stream().map(Match::getRound).max(Integer::compareTo).orElseThrow();
        var winners = bracket.getMatches().stream()
                .filter(m -> (m.getRound() == round)).map(Match::getResult)
                .map(MatchResult::getWinnerId).collect(Collectors.toList());
        if (winners.size() == 1) {
            LOG.info("call tournament.finish()");
            return null;
        }
        else if (winners.size() % 2 != 0) {
            // todo: change error message
            throw new RuntimeException("why tho");
        }
        else {
            if (bracket.getTournamentType() == TournamentType.SINGLE_ELIMINATION) {
                for (int i = 0; i < winners.size() - 1; i += 2) {
                    var matchDto = MatchDto.builder()
                            .round(round + 1)
                            .team1Id(winners.get(i))
                            .team2Id(winners.get(i + 1))
                            .bracket(bracket)
                            .build();
                    this.matchService.createNewMatch(matchDto);
                }
            }
        }
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