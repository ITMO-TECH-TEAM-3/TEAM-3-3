package com.tech.tournaments.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="teamrelationship", schema = "public")
public class TeamRelationship {
    /**
     * ИД сущности
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    /**
     * Турнир
     */
    @ToString.Exclude
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="tournament_id")
    private Tournament tournament;

    /**
     * ИД команды
     */
    private UUID team;
}
