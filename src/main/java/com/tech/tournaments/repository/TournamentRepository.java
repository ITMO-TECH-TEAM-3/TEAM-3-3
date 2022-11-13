package com.tech.tournaments.repository;

import com.tech.tournaments.model.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface TournamentRepository extends JpaRepository<Tournament, UUID> {

    /**
     * Получение турнира по id сетки
     *
     * @param bracket - bracket id
     * @return - bracket
     */
    Tournament findTournamentByBracketId(@Param("bracket_id") UUID bracket);
}
