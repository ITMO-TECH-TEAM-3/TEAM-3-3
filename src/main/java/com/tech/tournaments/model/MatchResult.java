package com.tech.tournaments.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "matchresult", schema = "public")
public class MatchResult {
    /**
     * id результата матча
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
     * счёт первой команды
     */
    private int score1;

    /**
     * счёт второй команды
     */
    private int score2;
}
