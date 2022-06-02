package com.tech.tournaments.model;

import com.tech.tournaments.model.enums.MatchStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class Match {
    /**
     * id матча
     */
    private UUID id;

    /**
     * id результата
     */
    private UUID resultId;

    /**
     * Номер круга
     */
    private int round;

    /**
     * Дата и время начала матча
     */
    private LocalDateTime startDateTime;

    /**
     * id первой команды
     */
    private UUID team1Id;

    /**
     * id второй команды
     */
    private UUID team2Id;

    /**
     * Статус матча
     */
    private MatchStatus matchStatus;
}
