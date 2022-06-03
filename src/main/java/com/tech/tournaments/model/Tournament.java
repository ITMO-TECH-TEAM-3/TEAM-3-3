package com.tech.tournaments.model;

import com.tech.tournaments.model.enums.TournamentStatus;
import com.tech.tournaments.model.enums.TournamentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="tournament", schema = "public")
public class Tournament {
    /**
     * ИД турнира
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    /**
     * ИД создателя
     */
    private UUID creatorId;
    /**
     * Тип турнира
     */
    @Enumerated(EnumType.STRING)
    private TournamentType tournamentType;
    /**
     * Зарегистрированные команды
     */
    @OneToMany(mappedBy="tournament")
    private List<TeamRelationship> teams;
    /**
     * Дата и время начала турнира
     */
    private LocalDateTime startDateTime;
    /**
     * Статус турнира
     */
    @Enumerated(EnumType.STRING)
    private TournamentStatus tournamentStatus;

    @OneToOne
    @JoinColumn(name="bracket_id")
    private Bracket bracket;
}
