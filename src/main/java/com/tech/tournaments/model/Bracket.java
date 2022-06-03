package com.tech.tournaments.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tech.tournaments.model.enums.TournamentType;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="bracket", schema = "public",
        indexes = {@Index(columnList="tournament_id", unique = true)})
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
    @ToString.Exclude
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="tournament_id")
    private Tournament tournament;
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
