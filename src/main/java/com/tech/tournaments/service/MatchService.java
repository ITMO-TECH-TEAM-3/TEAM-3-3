package com.tech.tournaments.service;

import com.tech.tournaments.model.Match;
import com.tech.tournaments.model.MatchResult;
import com.tech.tournaments.model.dto.MatchDto;
import com.tech.tournaments.model.dto.MatchResultDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface MatchService {
    /**
     * Создание нового матча
     *
     * @param matchDto - информация о матче
     * @return - информация о матче
     */
    Match createNewMatch(MatchDto matchDto);

    /**
     * Получение матча по id
     * @param id - id матча
     * @return - информация о матче
     */
    Match getMatchById(UUID id);

    /**
     * Получение списка матчей, проводимых в данную дату
     * @param date - дата проведения матчей
     * @return - список матчей, проводимых в данную дату
     */
    List<Match> getMatchesByDate(LocalDate date);

    /**
     * Получение списка матчей, проводимых в данном турнире
     * @param id - id турнира
     * @return - список матчей, проводимых в данном турнире
     */
    List<Match> getMatchesByTournamentId(UUID id);

    /**
     * Получение результата по id результата
     * @param id - id результата
     * @return - информация о результате
     */
    MatchResult getResultById(UUID id);

    /**
     * Получение результата по id матча
     * @param matchId - id матча
     * @return - информация о результате матча
     */
    MatchResult getResultByMatchId(UUID matchId);

    /**
     * Начало матча
     *
     * @param id - id матча
     */
    void startMatch(UUID id);

    /**
     * Отмена матча
     *
     * @param id - id матча
     */
    void cancelMatch(UUID id);

    /**
     * Завершение матча
     *
     * @param id - id матча
     * @param matchResultDto - информация о результате матча
     */
    void finishMatch(UUID id, MatchResultDto matchResultDto);
}