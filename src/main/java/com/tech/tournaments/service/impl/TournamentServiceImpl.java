package com.tech.tournaments.service.impl;

import com.tech.tournaments.service.TournamentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TournamentServiceImpl implements TournamentService {
    /**
     * {@inheritDoc}
     */
    @Override
    public String helloWorld() {
        LOG.info("Hello World");
        return "Hello World";
    }
}
