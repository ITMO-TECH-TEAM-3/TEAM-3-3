package com.tech.tournaments.service;

import com.tech.tournaments.model.Bracket;
import com.tech.tournaments.model.Tournament;

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
}