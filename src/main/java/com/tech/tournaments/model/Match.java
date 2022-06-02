package com.tech.tournaments.model;

import com.tech.tournaments.model.enums.MatchStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@Table(name = "match", schema = "public")
public class Match {
    /**
     * id матча
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    @Enumerated(EnumType.STRING)
    private MatchStatus matchStatus;
}
