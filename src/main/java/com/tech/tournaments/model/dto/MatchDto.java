package com.tech.tournaments.model.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class MatchDto {
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
}
