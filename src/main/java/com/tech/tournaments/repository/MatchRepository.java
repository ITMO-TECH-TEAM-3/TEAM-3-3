package com.tech.tournaments.repository;

import com.tech.tournaments.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MatchRepository extends JpaRepository<Match, UUID> {
}
