package com.tech.tournaments.model.dto;

import com.tech.tournaments.model.enums.TournamentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TournamentDto {
    /**
     * ИД создателя
     */
    private UUID creatorId;
    /**
     * Тип турнира
     */
    private TournamentType tournamentType;
    /**
     * Дата и время начала турнира
     */
    private LocalDateTime startDateTime;
}
