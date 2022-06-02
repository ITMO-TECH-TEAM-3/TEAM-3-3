package com.tech.tournaments.service.impl;

import com.tech.tournaments.model.Tournament;
import com.tech.tournaments.model.dto.TournamentDto;
import com.tech.tournaments.model.enums.TournamentStatus;
import com.tech.tournaments.repository.TournamentRepository;
import com.tech.tournaments.service.TournamentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class TournamentServiceImpl implements TournamentService {
    private final TournamentRepository tournamentRepository;

    @Autowired
    public TournamentServiceImpl(
            TournamentRepository tournamentRepository
    ) {
        this.tournamentRepository = tournamentRepository;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public Tournament createNewTournament(TournamentDto tournamentDto) {
        LOG.info("Creating new tournament: {}", tournamentDto);
        // todo: implement logic
        var tournament = new Tournament();
        tournament.setCreatorId(tournamentDto.getCreatorId());
        tournament.setStartDateTime(tournamentDto.getStartDateTime());
        tournament.setTournamentType(tournamentDto.getTournamentType());
        tournament.setTournamentStatus(TournamentStatus.PENDING);
        try {
            return this.tournamentRepository.save(tournament);
        } catch (Exception e) {
            LOG.error("Failed to create tournament: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Tournament getTournamentById(UUID id) {
        LOG.info("Get tournament by id: {}", id);
        // todo: implement logic
        return this.tournamentRepository.getById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addTeamInTournament(UUID id, UUID teamId) {
        LOG.info("Add team {} in tournament by id: {}", teamId, id);
        // todo: implement logic
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeTeamFromTournament(UUID id, UUID teamId) {
        LOG.info("Remove team {} from tournament by id: {}", teamId, id);
        // todo: implement logic
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startTournament(UUID id) {
        LOG.info("Start tournament by id: {}", id);
        // todo: implement logic
        var entity = this.tournamentRepository.getById(id);
        entity.setTournamentStatus(TournamentStatus.ONGOING);
        this.tournamentRepository.save(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void cancelTournament(UUID id) {
        LOG.info("Cancel tournament by id: {}", id);
        // todo: implement logic
        var entity = this.tournamentRepository.getById(id);
        entity.setTournamentStatus(TournamentStatus.CANCELLED);
        this.tournamentRepository.save(entity);
    }
}
