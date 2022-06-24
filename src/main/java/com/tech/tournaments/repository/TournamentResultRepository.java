package com.tech.tournaments.repository;

import com.tech.tournaments.model.TournamentResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TournamentResultRepository extends JpaRepository<TournamentResult, UUID> {
}
