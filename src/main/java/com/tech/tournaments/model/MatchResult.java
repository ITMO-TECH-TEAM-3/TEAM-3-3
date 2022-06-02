package com.tech.tournaments.model;

import java.util.UUID;

public class MatchResult {
    /**
     * id результата матча
     */
    private UUID id;

    /**
     * Ничья
     */
    private boolean isDraw;

    /**
     * id победителя
     */
    private UUID winnerId;

    /**
     * id матча
     */
    private UUID matchId;

    /**
     * счёт первой команды
     */
    private int score1;

    /**
     * счёт второй команды
     */
    private int score2;
}
