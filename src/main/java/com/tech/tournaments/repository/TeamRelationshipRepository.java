package com.tech.tournaments.repository;

import com.tech.tournaments.model.TeamRelationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TeamRelationshipRepository extends JpaRepository<TeamRelationship, UUID> {

    void deleteByTournamentIdAndTeam(@Param("tournament_id") UUID tournament, @Param("team") UUID id);

    List<TeamRelationship> findByTournamentId(@Param("tournament_id") UUID tournament);
}
