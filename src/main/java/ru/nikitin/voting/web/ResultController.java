package ru.nikitin.voting.web;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.nikitin.voting.service.DishService;
import ru.nikitin.voting.service.VoteService;
import ru.nikitin.voting.to.DishTo;
import ru.nikitin.voting.to.vote.VotingTo;

import java.time.LocalDate;
import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping(value = ResultController.MENU_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ResultController {

    private final VoteService voteService;
    private final DishService dishService;

    public static final String MENU_URL = "/api/result/";


    @GetMapping("voting/byDate")
    public List<VotingTo> getVoting(@RequestParam LocalDate date) {
        log.info("getVoting date = {} ", date.toString());
        return voteService.getVotingByDate(date);
    }

    @GetMapping("dishes/byDate")
    public List<DishTo> getDishes(@RequestParam LocalDate date) {
        log.info("getVoting date = {} ", date.toString());
        return dishService.getAllByDate(date);
    }
}
