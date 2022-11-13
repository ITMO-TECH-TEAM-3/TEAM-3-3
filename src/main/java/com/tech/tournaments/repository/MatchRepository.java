package com.tech.tournaments.repository;

import com.tech.tournaments.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface MatchRepository extends JpaRepository<Match, UUID> {

    /**
     * Достать матчи из сетки
     *
     * @param bracket id сетки
     * @return список матчей
     */
    List<Match> findByBracketId(@Param("bracket_id") UUID bracket);
}
