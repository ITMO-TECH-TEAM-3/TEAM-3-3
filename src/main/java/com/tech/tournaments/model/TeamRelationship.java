package com.tech.tournaments.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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
    @ManyToOne
    @JoinColumn(name="tournament_id")
    private Tournament tournament;

    /**
     * ИД команды
     */
    private UUID team;
}
