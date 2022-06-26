package com.tech.tournaments.repository;

import com.tech.tournaments.model.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

public interface TournamentRepository extends JpaRepository<Tournament, UUID> {

    Tournament findTournamentByBracketId(@Param("bracket_id") UUID bracket);
}
