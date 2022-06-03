package com.tech.tournaments.service.impl;

import com.tech.tournaments.model.Match;
import com.tech.tournaments.model.MatchResult;
import com.tech.tournaments.model.dto.MatchDto;
import com.tech.tournaments.model.dto.MatchResultDto;
import com.tech.tournaments.repository.MatchRepository;
import com.tech.tournaments.service.MatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class MatchServiceImpl implements MatchService {

    private final MatchRepository matchRepository;

    @Autowired
    public MatchServiceImpl(MatchRepository matchRepository)
    {
        this.matchRepository = matchRepository;
    }

    /**
     * {@inheritDoc}
     */
    public Match createNewMatch(MatchDto matchDto) {
        LOG.info("Create a new match: {}", matchDto);
        var match = Match.builder()
                .round(matchDto.getRound())
                .team1Id(matchDto.getTeam1Id())
                .team2Id(matchDto.getTeam2Id())
                .startDateTime(matchDto.getStartDateTime())
                .bracket(matchDto.getBracket())
                .build();
        return this.matchRepository.save(match);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Match getMatchById(UUID id) {
        LOG.info("Get match by id {}", id);
        // todo: implement logic
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Match> getMatchesByDate(LocalDate date) {
        LOG.info("Get matches by date {}", date);
        // todo: implement logic
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Match> getMatchesByTournamentId(UUID id) {
        LOG.info("Get matches by tournament id {}", id);
        // todo: implement logic
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MatchResult getResultById(UUID id) {
        LOG.info("Get result by id {}", id);
        // todo: implement logic
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MatchResult getResultByMatchId(UUID matchId) {
        LOG.info("Get result by match id {}", matchId);
        // todo: implement logic
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startMatch(UUID id) {
        LOG.info("Start a match {}", id);
        // todo: implement logic
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void finishMatch(UUID id, MatchResultDto matchResultDto) {
        LOG.info("Finish a match {}, {}", id, matchResultDto);
        // todo: implement logic
    }
}
