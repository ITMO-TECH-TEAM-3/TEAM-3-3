package com.tech.tournaments.service.impl;

import com.tech.tournaments.model.Match;
import com.tech.tournaments.model.MatchResult;
import com.tech.tournaments.model.dto.MatchDto;
import com.tech.tournaments.model.dto.MatchResultDto;
import com.tech.tournaments.service.MatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class MatchServiceImpl implements MatchService {

    @Override
    public Match createNewMatch(MatchDto matchDto) {
        LOG.info("Create a new match: {}", matchDto);
        // todo: implement logic
        return null;
    }

    @Override
    public Match getMatchById(UUID id) {
        LOG.info("Get match by id {}", id);
        // todo: implement logic
        return null;
    }

    @Override
    public List<Match> getMatchesByDate(LocalDate date) {
        LOG.info("Get matches by date {}", date);
        // todo: implement logic
        return null;
    }

    @Override
    public List<Match> getMatchesByTournamentId(UUID id) {
        LOG.info("Get matches by tournament id {}", id);
        // todo: implement logic
        return null;
    }

    @Override
    public MatchResult getResultById(UUID id) {
        LOG.info("Get result by id {}", id);
        // todo: implement logic
        return null;
    }

    @Override
    public MatchResult getResultByMatchId(UUID matchId) {
        LOG.info("Get result by match id {}", matchId);
        // todo: implement logic
        return null;
    }

    @Override
    public void startMatch(UUID id) {
        LOG.info("Start a match {}", id);
        // todo: implement logic
    }

    @Override
    public void finishMatch(UUID id, MatchResultDto matchResultDto) {
        LOG.info("Finish a match {}, {}", id, matchResultDto);
        // todo: implement logic
    }
}
