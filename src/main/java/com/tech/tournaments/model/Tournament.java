package com.tech.tournaments.model;

import com.tech.tournaments.model.enums.TournamentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Tournament {
    /**
     * ИД турнира
     */
    private UUID id;
    /**
     * ИД создателя
     */
    private UUID creatorId;
    /**
     * Тип турнира
     */
    private TournamentType tournamentType;
    /**
     * Зарегистрированные команды
     */
    private List<UUID> teams;
    /**
     * Дата и время начала турнира
     */
    private LocalDateTime startDateTime;
    /**
     * Статус турнира
     */
    private TournamentStatus tournamentStatus;
}
