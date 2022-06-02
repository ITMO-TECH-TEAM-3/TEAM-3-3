package com.tech.tournaments.service.feign;

import com.tech.tournaments.model.MatchResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(url = "${service.bets.url:}")
public interface BetsFeign
{
    /**
     * Отправка информации об окончании матча (результаты: счет, победитель и т.п.)
     *
     * @param matchResult информация о результате матча
     */
    @PostMapping("/results/match")
    void sendMatchResult(@RequestBody MatchResult matchResult);
}