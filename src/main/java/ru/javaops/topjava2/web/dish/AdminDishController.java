package ru.javaops.topjava2.web.dish;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.topjava2.service.DishService;
import ru.javaops.topjava2.to.DishTo;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = AdminDishController.DISH_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class AdminDishController {

    public static final String DISH_URL = "/api/admin/restaurants/{restaurantId}/dishes";

    private final DishService service;

    public AdminDishController(DishService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public DishTo get(@PathVariable int restaurantId, @PathVariable int id) {
        log.info("get dish id={} with restaurantId={}", id, restaurantId);
        return service.get(restaurantId, id);
    }

    @GetMapping
    public Map<LocalDate, List<DishTo>> getAllMenu(@PathVariable Integer restaurantId) {
        return service.getAllMenu(restaurantId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int restaurantId, @PathVariable int id) {
        service.delete(restaurantId, id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int restaurantId, @Valid @RequestBody DishTo dishTo, @PathVariable int id) {
        log.info("restaurant with id={} update {} with id={}", restaurantId, dishTo, id);
        service.update(restaurantId, dishTo, id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DishTo> create(@PathVariable int restaurantId, @Valid @RequestBody DishTo dishTo) {
        DishTo created = service.create(restaurantId, dishTo);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(DISH_URL + "/{id}")
                .buildAndExpand(restaurantId, created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

}
