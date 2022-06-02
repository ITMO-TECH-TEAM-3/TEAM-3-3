package com.tech.tournaments.service.impl;

import com.tech.tournaments.model.TeamRelationship;
import com.tech.tournaments.model.Tournament;
import com.tech.tournaments.model.dto.TournamentDto;
import com.tech.tournaments.model.enums.TournamentStatus;
import com.tech.tournaments.repository.TeamRelationshipRepository;
import com.tech.tournaments.repository.TournamentRepository;
import com.tech.tournaments.service.TournamentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@Slf4j
public class TournamentServiceImpl implements TournamentService {
    private final TournamentRepository tournamentRepository;
    private final TeamRelationshipRepository teamRelationshipRepository;

    @Autowired
    public TournamentServiceImpl(TournamentRepository tournamentRepository,
                                 TeamRelationshipRepository teamRelationshipRepository) {
        this.tournamentRepository = tournamentRepository;
        this.teamRelationshipRepository = teamRelationshipRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Tournament createNewTournament(TournamentDto tournamentDto) {
        LOG.info("Creating new tournament: {}", tournamentDto);
        var tournament = Tournament.builder()
                .creatorId(tournamentDto.getCreatorId())
                .startDateTime(tournamentDto.getStartDateTime())
                .tournamentType(tournamentDto.getTournamentType())
                .tournamentStatus(TournamentStatus.PENDING)
                .build();
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
        return this.tournamentRepository.findById(id)
                .orElseThrow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addTeamInTournament(UUID id, UUID teamId) {
        LOG.info("Add team {} in tournament by id: {}", teamId, id);
        var teamRelationship = TeamRelationship.builder()
                .tournament(getTournamentById(id))
                .team(teamId)
                .build();
        this.teamRelationshipRepository.save(teamRelationship);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void removeTeamFromTournament(UUID id, UUID teamId) {
        LOG.info("Remove team {} from tournament by id: {}", teamId, id);
        this.teamRelationshipRepository.deleteByTournamentIdAndTeam(id, teamId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startTournament(UUID id) {
        LOG.info("Start tournament by id: {}", id);
        var entity = getTournamentById(id);
        entity.setTournamentStatus(TournamentStatus.ONGOING);
        this.tournamentRepository.save(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void cancelTournament(UUID id) {
        LOG.info("Cancel tournament by id: {}", id);
        var entity = getTournamentById(id);
        entity.setTournamentStatus(TournamentStatus.CANCELLED);
        this.tournamentRepository.save(entity);
    }
}
