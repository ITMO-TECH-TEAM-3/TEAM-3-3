package com.tech.tournaments.service.feign;

import com.tech.tournaments.model.external.Team;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@FeignClient(name = "users", url = "${service.users.url:}")
public interface UsersFeign {
    /**
     * Получение информации о команде (результаты: счет, победитель и т.п.)
     *
     * @param id id команды
     * @return информация о команде
     */
    @PostMapping("/teams-rest/{id}")
    Team getTeamInfo(@PathVariable UUID id);
}