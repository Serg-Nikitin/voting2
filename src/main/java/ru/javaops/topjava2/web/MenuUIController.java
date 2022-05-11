package ru.javaops.topjava2.web;


import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javaops.topjava2.service.VoteService;
import ru.javaops.topjava2.to.VotingTo;

import java.time.LocalDate;

@RestController
@Slf4j
@RequestMapping(value = MenuUIController.MENU_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuUIController {

    private final VoteService service;

    public static final String MENU_URL = "/api/menu";

    public MenuUIController(VoteService service) {
        this.service = service;
    }

    @GetMapping("/{date}")
    public VotingTo getVoting(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return service.getVoting(date);
    }
}
