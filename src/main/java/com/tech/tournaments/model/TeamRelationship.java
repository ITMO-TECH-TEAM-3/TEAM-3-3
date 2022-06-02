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
@Table(name="teamrelationship", schema = "public")
public class TeamRelationship {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(targetEntity = Tournament.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn()
    private Tournament tournament;
    private UUID team;
}
