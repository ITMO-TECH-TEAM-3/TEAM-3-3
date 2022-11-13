package com.tech.tournaments.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tech.tournaments.model.enums.MatchStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
    @OneToOne(cascade=CascadeType.PERSIST)
    private MatchResult result;

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

    /**
     * Сетка
     */
    @ToString.Exclude
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="bracket_id")
    private Bracket bracket;
}
