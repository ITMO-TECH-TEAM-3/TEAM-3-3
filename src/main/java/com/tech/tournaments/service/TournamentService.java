package com.tech.tournaments.service;

import com.tech.tournaments.model.Tournament;
import com.tech.tournaments.model.dto.TournamentDto;

import java.util.List;
import java.util.UUID;

public interface TournamentService {
    /**
     * Создание турнира
     *
     * @return созданная сущность турнира
     */
    Tournament createNewTournament(TournamentDto tournamentDto);

    /**
     * Получение турнира по ИД
     *
     * @param id - ид турнира
     * @return - информация о турнире
     */
    Tournament getTournamentById(UUID id);

    /**
     *
     * @param id - bracket id
     * @return - bracket
     */
    Tournament getTournamentByBracketId(UUID id);

    /**
     * Добавление команды в турнир по ИД
     *
     * @param id     - ид турнира
     * @param teamId - ид команды
     */
    void addTeamInTournament(UUID id, UUID teamId);

    /**
     * Исключение команды из турнира по ИД
     *
     * @param id     - ид турнира
     * @param teamId - ид команды
     */
    void removeTeamFromTournament(UUID id, UUID teamId);

    /**
     * Досрочное начало турнира
     *
     * @param id - ид турнира
     */
    void startTournament(UUID id);

    /**
     * Отмена турнира
     *
     * @param id - ид турнира
     */
    void cancelTournament(UUID id);

    /**
     * Обновление турнира по завершении всех текущих матчей
     *
     * @param id - ид турнира
     */
    void processNewRound(UUID id);

    /**
     * Получение информации о командах по ИД
     *
     * @param id - ид турнира
     * @return - информация о командах в турнире
     */
    List<UUID> getTournamentTeamsById(UUID id);
}
