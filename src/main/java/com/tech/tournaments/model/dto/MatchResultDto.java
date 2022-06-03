package com.tech.tournaments.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MatchResultDto {
    /**
     * Ничья
     */
    private boolean isDraw;

    /**
     * id победителя
     */
    private UUID winnerId;

    /**
     * счёт первой команды
     */
    private int score1;

    /**
     * счёт второй команды
     */
    private int score2;
}
