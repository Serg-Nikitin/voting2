package ru.nikitin.voting.web.vote;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.nikitin.voting.service.VoteService;
import ru.nikitin.voting.to.vote.VoteTo;
import ru.nikitin.voting.web.AuthUser;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping(value = ProfileVoteController.VOTE_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileVoteController {
    public static final String VOTE_URL = "/api/profile/votes";

    private final VoteService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<VoteTo> voting(@AuthenticationPrincipal AuthUser authUser, @RequestParam @Nullable int restaurantId) {
        log.info("voting userId = {}, restaurantId = {}", authUser.id(), restaurantId);
        VoteTo created = service.voting(authUser.id(), restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(VOTE_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping
    public List<VoteTo> getAll(@AuthenticationPrincipal AuthUser authUser) {
        log.info("getAll vote with userId={}", authUser.getUser().getId());
        return service.getAll(authUser.id());
    }

    @GetMapping("/onDate")
    public VoteTo getVoteByDate(@AuthenticationPrincipal AuthUser authUser, @RequestParam @Nullable LocalDate date) {
        log.info("getVoteThisDay day={}", date);
        return service.getByDate(authUser.id(), date);
    }
}
