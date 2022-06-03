package com.tech.tournaments.model;

import com.tech.tournaments.model.enums.TournamentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="bracket", schema = "public",
        indexes = {@Index(columnList="tournamentId", unique = true)})
public class Bracket {
    /**
     * ИД сетки
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    /**
     * ИД турнира
     */
    private UUID tournamentId;
    /**
     * Тип турнира (и сетки)
     */
    @Enumerated(EnumType.STRING)
    private TournamentType tournamentType;

    /**
     * Список матчей
     */
    @OneToMany(mappedBy="bracket")
    private List<Match> matches;
}
