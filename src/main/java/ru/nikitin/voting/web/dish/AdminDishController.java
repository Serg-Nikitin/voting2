package ru.nikitin.voting.web.dish;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.nikitin.voting.model.Dish;
import ru.nikitin.voting.service.DishService;
import ru.nikitin.voting.to.DishTo;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public DishTo get(@PathVariable Integer restaurantId, @PathVariable int id) {
        log.info("get dish id={} with restaurantId={}", id, restaurantId);
        return service.get(restaurantId, id);
    }

    @GetMapping
    public Map<LocalDate, List<DishTo>> getAllMenu(@PathVariable Integer restaurantId) {
        log.info("getAllMenu restaurantId = {}", restaurantId);
        Map<LocalDate, List<DishTo>> allMenu = service.findAll().stream()
                .filter(dish -> dish.getRestaurant().id() == restaurantId)
                .collect(Collectors.groupingBy(Dish::getServingDate, Collectors.mapping(DishTo::new, Collectors.toList())));
        if (allMenu.size() == 0) {
            throw new EntityNotFoundException(String.format("Restaurant's menu with id = %d not found", restaurantId));
        }
        return allMenu;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int restaurantId, @PathVariable int id) {
        log.info("delete with id={} and restaurantId={}", id, restaurantId);
        service.delete(restaurantId, id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int restaurantId, @Valid @RequestBody DishTo dishTo, @PathVariable int id) {
        log.info("update with id={} restaurantId = {} and data ={}", id, restaurantId, dishTo);
        service.update(restaurantId, dishTo, id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DishTo> create(@PathVariable int restaurantId, @Valid @RequestBody DishTo dishTo) {
        log.info("create dish with restaurantId = {}, data = {}", restaurantId, dishTo);
        DishTo created = service.create(restaurantId, dishTo);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(DISH_URL + "/{id}")
                .buildAndExpand(restaurantId, created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
/*
    @GetMapping("/{date}")
    public List<DishTo> getAllMenuOnDate(@PathVariable Integer restaurantId, @PathVariable LocalDate date){
        log.info("getAllMenuOnDate restaurantId = {}, date = {}", restaurantId, date);

    }*/
}
