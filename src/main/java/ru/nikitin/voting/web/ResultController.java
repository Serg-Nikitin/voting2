package ru.nikitin.voting.web;


import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.nikitin.voting.service.VoteService;
import ru.nikitin.voting.to.vote.VotingTo;

import java.time.LocalDate;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = ResultController.MENU_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ResultController {

    private final VoteService service;

    public static final String MENU_URL = "/api/voting";

    public ResultController(VoteService service) {
        this.service = service;
    }

    @GetMapping("/byDate")
    public List<VotingTo> getVoting(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("getVoting date = {} ", date.toString());
        return service.getVotingByDate(date);
    }
}
