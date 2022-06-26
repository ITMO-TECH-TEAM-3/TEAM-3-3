package com.tech.tournaments.controller;

import com.tech.tournaments.model.Match;
import com.tech.tournaments.model.MatchResult;
import com.tech.tournaments.model.dto.MatchResultDto;
import com.tech.tournaments.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/match")
public class MatchController {
    private final MatchService matchService;

    @Autowired
    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    /**
     * Получение матча по id
     *
     * @param id - id матча
     * @return - информация о матче
     */
    @GetMapping("/{id}")
    public Match getMatchById(@PathVariable UUID id) {
        return this.matchService.getMatchById(id);
    }

    /**
     * Получение результата матча по id матча
     *
     * @param id - id матча
     * @return - информация о результате матча
     */
    @GetMapping("/by-match/{id}")
    public MatchResult getResultByMatchId(@PathVariable UUID id) {
        return this.matchService.getResultByMatchId(id);
    }

    /**
     * Получение результата матча по id результата
     *
     * @param id - id результата
     * @return - информация о результате матча
     */
    @GetMapping("/by-result/{id}")
    public MatchResult getResultById(@PathVariable UUID id) {
        return this.matchService.getResultById(id);
    }

    /**
     * Получение списка матчей, проводимых в данную дату
     *
     * @param date - дата проведения матчей
     * @return - список матчей, проводимых в данную дату
     */
    @GetMapping("/get-by-date")
    public List<Match> getMatchesByDate(@RequestParam LocalDate date) {
        return this.matchService.getMatchesByDate(date);
    }

    /**
     * Получение списка матчей, проводимых в данном турнире
     *
     * @param id - id турнира
     * @return - список матчей, проводимых в данном турнире
     */
    @GetMapping("/get-by-tournament-id")
    public List<Match> getMatchesByTournamentId(@RequestParam UUID id) {
        return this.matchService.getMatchesByTournamentId(id);
    }

    /**
     * Начало матча
     *
     * @param id - id матча
     */
    @PutMapping("/{id}/start")
    public void startMatch(@PathVariable UUID id) {
        this.matchService.startMatch(id);
    }

    /**
     * Отмена матча
     *
     * @param id - id матча
     */
    @PutMapping("/{id}/cancel")
    public void cancelMatch(@PathVariable UUID id) {
        this.matchService.cancelMatch(id);
    }

    /**
     * Завершение матча
     *
     * @param id - id матча
     * @param matchResultDto - информация о результате матча
     */
    @PutMapping("/{id}/finish")
    public void finishMatch(@PathVariable UUID id,
                            @RequestBody MatchResultDto matchResultDto) {
        this.matchService.finishMatch(id, matchResultDto);
    }
}
