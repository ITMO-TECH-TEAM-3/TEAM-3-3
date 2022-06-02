package com.tech.tournaments.repository;

import com.tech.tournaments.model.TeamRelationship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TeamRelationshipRepository extends JpaRepository<TeamRelationship, UUID> {
}
