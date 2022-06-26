package com.tech.tournaments.model.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Team {
    private UUID id;
    private String name;
    private UUID creatorId;
}
