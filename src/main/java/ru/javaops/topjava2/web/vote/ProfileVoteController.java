package ru.javaops.topjava2.web.vote;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.javaops.topjava2.model.Vote;
import ru.javaops.topjava2.service.VoteService;
import ru.javaops.topjava2.web.AuthUser;

@RestController
@Slf4j
@RequestMapping(value = ProfileVoteController.VOTE_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileVoteController {
    public static final String VOTE_URL = "/api/profile/votes";

    private final VoteService service;

    public ProfileVoteController(VoteService service) {
        this.service = service;
    }


    @GetMapping("/{restaurantId}")
    @ResponseStatus(HttpStatus.OK)
    public Vote voting(@AuthenticationPrincipal AuthUser authUser, @PathVariable int restaurantId) {
        return service.voting(authUser.id(), restaurantId);
    }
}
