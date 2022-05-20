package ru.nikitin.voting.web.vote;

import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.nikitin.voting.model.Vote;
import ru.nikitin.voting.service.VoteService;
import ru.nikitin.voting.web.AuthUser;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = ProfileVoteController.VOTE_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileVoteController {
    public static final String VOTE_URL = "/api/profile/votes";

    private final VoteService service;

    public ProfileVoteController(VoteService service) {
        this.service = service;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Vote> voting(@AuthenticationPrincipal AuthUser authUser, @RequestParam int restaurantId) {
        log.info("voting userId = {}, restaurantId = {}", authUser.id(), restaurantId);
        Vote created = service.voting(authUser.id(), restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(VOTE_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{voteId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeVote(@AuthenticationPrincipal AuthUser authUser, @RequestParam @Nullable int restaurantId, @PathVariable int voteId) {
        log.info("update vote with id={}, userId{}, restaurantId{}", voteId, authUser.id(), restaurantId);
        service.changeVote(authUser.id(), restaurantId, voteId);
    }

    @GetMapping
    public List<Vote> getAll(@AuthenticationPrincipal AuthUser authUser) {
        log.info("getAll vote with userId={}", authUser.getUser().getId());
        return service.getAll(authUser.id());
    }

    @GetMapping("/onDate")
    public Vote getVoteThisDay(@RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, @AuthenticationPrincipal AuthUser authUser) {
        log.info("getVoteThisDay day={}", date);
        return service.getVoteThisDay(date, authUser.id());
    }
}
