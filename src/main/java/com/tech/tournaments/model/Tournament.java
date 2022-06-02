package com.tech.tournaments.model;

import com.tech.tournaments.model.enums.TournamentStatus;
import com.tech.tournaments.model.enums.TournamentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
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
    @OneToMany()
    @JoinColumn()
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
}
