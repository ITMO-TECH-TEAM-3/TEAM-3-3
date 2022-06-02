package com.tech.tournaments.repository;

import com.tech.tournaments.model.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

public interface TournamentRepository extends JpaRepository<Tournament, UUID> {
}
