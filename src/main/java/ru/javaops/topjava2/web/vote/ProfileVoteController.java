package ru.javaops.topjava2.web.vote;

import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.topjava2.model.Vote;
import ru.javaops.topjava2.service.VoteService;
import ru.javaops.topjava2.web.AuthUser;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.javaops.topjava2.util.validation.ValidationUtil.assureIdConsistent;
import static ru.javaops.topjava2.util.validation.ValidationUtil.checkNew;

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
    public ResponseEntity<Vote> voting(@AuthenticationPrincipal AuthUser authUser, @Valid @RequestBody Vote vote) {
        log.info("voting {}", vote);
        checkNew(vote);
        Vote created = service.voting(authUser.id(), vote);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(VOTE_URL).build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{voteId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeVote(@AuthenticationPrincipal AuthUser authUser, @Valid @RequestBody Vote vote, @PathVariable int voteId) {
        log.info("update {} with id={}", vote, voteId);
        assureIdConsistent(vote, voteId);
        service.changeVote(authUser.id(), vote, voteId);
    }

    @GetMapping
    public List<Vote> getAll(@AuthenticationPrincipal AuthUser authUser) {
        return service.getAll(authUser.getUser());
    }

    @GetMapping("/{date}")
    public Vote getVoteThisDay(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, @AuthenticationPrincipal AuthUser authUser) {
        return service.getVoteThisDay(date, authUser.getUser());
    }
}
