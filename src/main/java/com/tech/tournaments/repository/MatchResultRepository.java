package com.tech.tournaments.repository;

import com.tech.tournaments.model.MatchResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MatchResultRepository extends JpaRepository<MatchResult, UUID> {
}
