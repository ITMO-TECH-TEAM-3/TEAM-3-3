package com.tech.tournaments.service.impl;

import com.tech.tournaments.model.Match;
import com.tech.tournaments.model.MatchResult;
import com.tech.tournaments.model.dto.MatchDto;
import com.tech.tournaments.model.dto.MatchResultDto;
import com.tech.tournaments.model.enums.MatchStatus;
import com.tech.tournaments.model.external.MatchResultExt;
import com.tech.tournaments.repository.MatchRepository;
import com.tech.tournaments.repository.MatchResultRepository;
import com.tech.tournaments.service.MatchService;
import com.tech.tournaments.service.TournamentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.tech.tournaments.model.enums.MatchStatus.*;

@Service
@Slf4j
public class MatchServiceImpl implements MatchService {

    @Value("${service.bets.url}")
    private String betsUrl;

    private final MatchRepository matchRepository;
    private final MatchResultRepository matchResultRepository;
    private final TournamentService tournamentService;
    private final RestTemplate restTemplate;

    @Autowired
    public MatchServiceImpl(MatchRepository matchRepository,
                            @Lazy TournamentService tournamentService,
                            MatchResultRepository matchResultRepository,
                            RestTemplate restTemplate) {
        this.matchRepository = matchRepository;
        this.tournamentService = tournamentService;
        this.matchResultRepository = matchResultRepository;
        this.restTemplate = restTemplate;
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
                .matchStatus(MatchStatus.PENDING)
                .build();

        try {
            return this.matchRepository.save(match);
        } catch (Exception e) {
            LOG.error("Failed to create match: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Match getMatchById(UUID id) {
        LOG.info("Get match by id {}", id);
        return this.matchRepository.findById(id)
                .orElseThrow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Match> getMatchesByDate(LocalDate date) {
        LOG.info("Get matches by date {}", date);
        return this.matchRepository.findAll()
                .stream()
                .filter(match -> match.getStartDateTime().toLocalDate().equals(date))
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Match> getMatchesByTournamentId(UUID id) {
        LOG.info("Get matches by tournament id {}", id);
        return this.matchRepository.findByBracketId(this.tournamentService.getTournamentById(id).getBracket().getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MatchResult getResultById(UUID id) {
        LOG.info("Get result by id {}", id);
        return this.matchResultRepository.findById(id)
                .orElseThrow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MatchResult getResultByMatchId(UUID matchId) {
        LOG.info("Get result by match id {}", matchId);
        return this.matchResultRepository.findById(
                this.matchRepository.findById(matchId)
                        .orElseThrow()
                        .getResult()
                        .getId())
                .orElseThrow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startMatch(UUID id) {
        LOG.info("Start a match {}", id);
        var match = getMatchById(id);
        if (match.getMatchStatus() != PENDING) {
            throw new RuntimeException("Match is not in pending state");
        }
        match.setMatchStatus(ONGOING);
        this.matchRepository.save(match);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void cancelMatch(UUID id) {
        LOG.info("Start a match {}", id);
        var match = getMatchById(id);
        if (match.getMatchStatus() != CANCELLED) {
            throw new RuntimeException("Can't cancel cancelled match");
        }
        match.setMatchStatus(CANCELLED);
        this.matchRepository.save(match);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void finishMatch(UUID id, MatchResultDto matchResultDto) {
        LOG.info("Finish a match {}, {}", id, matchResultDto);
        var match = getMatchById(id);
        if (match.getMatchStatus() != ONGOING) {
            throw new RuntimeException("Can't finish non-ongoing match");
        }
        match.setMatchStatus(FINISHED);
        var bracket = match.getBracket();

        var matchResult = createMatchResult(match, matchResultDto);
        match.setResult(matchResult);
        this.matchRepository.save(match);
        try {
            var matchResultExt = MatchResultExt.builder()
                    .isDraw(matchResult.isDraw())
                    .winnerTeamId(matchResult.getWinnerId())
                    .build();
            restTemplate.exchange(String.format("%s/route/update/match?matchId=%s", betsUrl, match.getId()),
                    HttpMethod.PATCH,
                    new HttpEntity<>(matchResultExt, new HttpHeaders()),
                    Void.class);
        } catch (Exception e)
        {
            LOG.warn("Failed to send info to Bets service");
        }

        var allMatchesFinished = bracket.getMatches()
                .stream()
                .map(Match::getMatchStatus)
                .allMatch(r -> r == FINISHED || r == CANCELLED);
        if (allMatchesFinished) {
            this.tournamentService.processNewRound(
                    this.tournamentService.getTournamentByBracketId(match.getBracket().getId()).getId());
        }
    }

    private MatchResult createMatchResult(Match match, MatchResultDto matchResultDto)
    {
        LOG.info("Create result for match: {}", match.getId());
        var matchResult = MatchResult.builder()
                .isDraw(matchResultDto.isDraw())
                .score1(matchResultDto.getScore1())
                .score2(matchResultDto.getScore2())
                .winnerId(matchResultDto.getWinnerId())
                .build();
        return matchResultRepository.save(matchResult);
    }
}
