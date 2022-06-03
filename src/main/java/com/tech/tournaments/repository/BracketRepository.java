package com.tech.tournaments.repository;

import com.tech.tournaments.model.Bracket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BracketRepository extends JpaRepository<Bracket, UUID> {
}
