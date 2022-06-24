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
    Bracket generateNewRoundForTournament(Tournament tournament, Bracket bracket);
}