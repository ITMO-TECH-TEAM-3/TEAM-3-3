package com.tech.tournaments.controller;

import com.tech.tournaments.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tournament")
public class TournamentController {
    private final TournamentService tournamentService;

    @Autowired
    public TournamentController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    // todo: remove
    /**
     * Test method
     *
     * @return "Hello World" string
     */
    @GetMapping
    public String helloWorld() {
        return this.tournamentService.helloWorld();
    }
}
