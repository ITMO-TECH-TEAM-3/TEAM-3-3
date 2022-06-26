package com.tech.tournaments.service;

import com.tech.tournaments.model.Bracket;
import com.tech.tournaments.model.Match;
import com.tech.tournaments.model.Tournament;

import java.util.List;
import java.util.UUID;

public interface BracketService {
    /**
     * Генерация сетки для турнира
     * @param tournament турнир
     * @return сетка
     */
    Bracket generateBracketForTournament(Tournament tournament);

    /**
     * Генерация нового раунда для турнира
     * @param tournament турнир
     * @return создались ли новые матчи
     */
    boolean generateNewRoundForTournament(Tournament tournament);

    /**
     * Достать матчи из сетки
     * @param id
     * @return список матчей
     */
    public List<Match> getBracketMatchesById(UUID id);
}