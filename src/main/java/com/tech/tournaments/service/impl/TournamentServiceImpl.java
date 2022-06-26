package com.tech.tournaments.service.impl;

import com.tech.tournaments.model.*;
import com.tech.tournaments.model.dto.TournamentDto;
import com.tech.tournaments.model.enums.TournamentStatus;
import com.tech.tournaments.repository.TeamRelationshipRepository;
import com.tech.tournaments.repository.TournamentRepository;
import com.tech.tournaments.repository.TournamentResultRepository;
import com.tech.tournaments.service.BracketService;
import com.tech.tournaments.service.MatchService;
import com.tech.tournaments.service.TournamentService;
import com.tech.tournaments.service.feign.BetsFeign;
import com.tech.tournaments.service.feign.UsersFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

import static com.tech.tournaments.model.enums.TournamentStatus.*;
import static com.tech.tournaments.model.enums.TournamentType.ROUND_ROBIN;
import static com.tech.tournaments.model.enums.TournamentType.SINGLE_ELIMINATION;

@Service
@Slf4j
public class TournamentServiceImpl implements TournamentService {

    private final TournamentRepository tournamentRepository;
    private final TournamentResultRepository tournamentResultRepository;
    private final TeamRelationshipRepository teamRelationshipRepository;
    private final BracketService bracketService;
    private final MatchService matchService;
    private final BetsFeign betsFeign;
    private final UsersFeign usersFeign;

    @Autowired
    public TournamentServiceImpl(TournamentRepository tournamentRepository,
                                 TournamentResultRepository tournamentResultRepository,
                                 TeamRelationshipRepository teamRelationshipRepository,
                                 @Lazy BracketService bracketService,
                                 @Lazy MatchService matchService,
                                 BetsFeign betsFeign,
                                 UsersFeign usersFeign) {
        this.tournamentRepository = tournamentRepository;
        this.tournamentResultRepository = tournamentResultRepository;
        this.teamRelationshipRepository = teamRelationshipRepository;
        this.bracketService = bracketService;
        this.matchService = matchService;
        this.betsFeign = betsFeign;
        this.usersFeign = usersFeign;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Tournament createNewTournament(TournamentDto tournamentDto) {
        LOG.info("Creating new tournament: {}", tournamentDto);
        var tournament = Tournament.builder()
                .creatorId(tournamentDto.getCreatorId())
                .startDateTime(tournamentDto.getStartDateTime())
                .tournamentType(tournamentDto.getTournamentType())
                .tournamentStatus(TournamentStatus.PENDING)
                .build();
        try {
            return this.tournamentRepository.save(tournament);
        } catch (Exception e) {
            LOG.error("Failed to create tournament: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Tournament getTournamentById(UUID id) {
        LOG.info("Get tournament by id: {}", id);
        return this.tournamentRepository.findById(id)
                .orElseThrow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Tournament getTournamentByBracketId(UUID id) {
        LOG.info("Get tournament by id: {}", id);
        return this.tournamentRepository.findTournamentByBracketId(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addTeamInTournament(UUID id, UUID teamId) {
        LOG.info("Add team {} in tournament by id: {}", teamId, id);

        var tournament = getTournamentById(id);
        if (tournament.getTournamentStatus() != PENDING) {
            throw new RuntimeException("Tournament is not in pending state");
        }

        try {
            var existTeam = this.usersFeign.getTeamInfo(teamId);
        } catch (Exception e) {
            LOG.warn("Team does not exist in users service");
        }

        var teamRelationship = TeamRelationship.builder()
                .tournament(getTournamentById(id))
                .team(teamId)
                .build();
        this.teamRelationshipRepository.save(teamRelationship);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void removeTeamFromTournament(UUID id, UUID teamId) {
        LOG.info("Remove team {} from tournament by id: {}", teamId, id);
        this.teamRelationshipRepository.deleteByTournamentIdAndTeam(id, teamId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startTournament(UUID id) {
        LOG.info("Start tournament by id: {}", id);
        var entity = getTournamentById(id);
        entity.setTournamentStatus(ONGOING);
        var bracket = this.bracketService.generateBracketForTournament(entity);
        entity.setBracket(bracket);
        this.tournamentRepository.save(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void cancelTournament(UUID id) {
        LOG.info("Cancel tournament by id: {}", id);
        var entity = getTournamentById(id);
        entity.setTournamentStatus(CANCELLED);
        this.tournamentRepository.save(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void processNewRound(UUID id) {
        var tournament = getTournamentById(id);
        var newRoundsGenerated = this.bracketService.generateNewRoundForTournament(tournament);
        if (!newRoundsGenerated) {
            finishTournament(id);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UUID> getTournamentTeamsById(UUID id) {
        LOG.info("Get tournament teams by id: {}", id);
        return teamRelationshipRepository.findByTournamentId(id)
                .stream()
                .map(TeamRelationship::getTeam)
                .collect(Collectors.toList());
    }

    private void finishTournament(UUID id) {
        LOG.info("Finish tournament by id: {}", id);
        var entity = getTournamentById(id);
        if (entity.getTournamentStatus() == CANCELLED || entity.getTournamentStatus() == FINISHED) {
            return;
        }
        entity.setTournamentStatus(FINISHED);
        var winner = determineWinner(entity)
                .orElseThrow();
        var tournamentResult = TournamentResult.builder()
                .winnerId(winner)
                .build();
        tournamentResultRepository.save(tournamentResult);
        entity.setTournamentResult(tournamentResult);
        this.tournamentRepository.save(entity);

        try {
            betsFeign.sendTournamentResult(tournamentResult);
        } catch (Exception e) {
            LOG.warn("Failed to send info to Bets service");
        }
    }

    private Optional<UUID> determineWinner(Tournament tournament) {
        LOG.info("Determine winner for tournament {}", tournament.getId());
        Optional<UUID> winner = Optional.empty();
        var matches = tournament.getBracket().getMatches();
        var matchResults = matches.stream()
                .map(m -> this.matchService.getResultByMatchId(m.getId()))
                .collect(Collectors.toList());
        if (tournament.getTournamentType() == ROUND_ROBIN) {
            winner = matchResults.stream()
                    .collect(Collectors.toMap(MatchResult::getWinnerId, (m) -> 1, Integer::sum))
                    .entrySet()
                    .stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey);
        } else if (tournament.getTournamentType() == SINGLE_ELIMINATION) {
            winner = matches.stream()
                    .max(Comparator.comparing(Match::getRound))
                    .map(Match::getResult)
                    .map(MatchResult::getWinnerId);
        }
        LOG.info("Determined winner {} for tournament {}", winner, tournament.getId());
        return winner;
    }
}
