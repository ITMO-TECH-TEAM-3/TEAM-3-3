package com.tech.tournaments.controller;

import com.tech.tournaments.model.Tournament;
import com.tech.tournaments.model.dto.TournamentDto;
import com.tech.tournaments.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/tournament")
public class TournamentController {
    private final TournamentService tournamentService;

    @Autowired
    public TournamentController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    /**
     * Создание турнира
     *
     * @return созданная сущность турнира
     */
    @PostMapping
    public Tournament create(@RequestBody TournamentDto tournamentDto) {
        return this.tournamentService.createNewTournament(tournamentDto);
    }

    /**
     * Получение турнира по ИД
     *
     * @param id - ид турнира
     * @return - информация о турнире
     */
    @GetMapping("/{id}")
    public Tournament getInfo(@PathVariable UUID id) {
        return this.tournamentService.getTournamentById(id);
    }

    /**
     * Добавление команды в турнир по ИД
     *
     * @param id     - ид турнира
     * @param teamId - ид команды
     */
    @PutMapping("/{id}/add-team")
    public void addTeam(@PathVariable UUID id,
                        @RequestParam UUID teamId) {
        this.tournamentService.addTeamInTournament(id, teamId);
    }

    /**
     * Исключение команды из турнира по ИД
     *
     * @param id     - ид турнира
     * @param teamId - ид команды
     */
    @DeleteMapping("/{id}/remove-team")
    public void removeTeam(@PathVariable UUID id,
                           @RequestParam UUID teamId) {
        this.tournamentService.removeTeamFromTournament(id, teamId);
    }

    /**
     * Досрочное начало турнира
     *
     * @param id - ид турнира
     */
    @PostMapping("/{id}/start")
    public void start(@PathVariable UUID id) {
        this.tournamentService.startTournament(id);
    }

    /**
     * Отмена турнира
     *
     * @param id - ид турнира
     */
    @PostMapping("/{id}/cancel")
    public void cancel(@PathVariable UUID id) {
        this.tournamentService.cancelTournament(id);
    }
}
