package ru.javaops.topjava2.web.dish;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.topjava2.model.Dish;
import ru.javaops.topjava2.service.DishService;

import javax.validation.Valid;
import java.net.URI;
import java.util.Comparator;
import java.util.List;
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
    public ResponseEntity<Dish> get(@PathVariable int restaurantId, @PathVariable int id) {
        log.info("get dish id={} with restaurantId={}", id, restaurantId);
        return ResponseEntity.of(service.get(restaurantId, id));
    }

    @GetMapping
    public List<Dish> getAllMenu(@PathVariable Integer restaurantId) {
        return service.getAll()
                .stream()
                .filter(dish -> restaurantId.equals(dish.getRestaurant().getId()))
                .sorted(Comparator.comparing(Dish::getDateOfServing).thenComparing(Dish::getId))
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int restaurantId, @PathVariable int id) {
        service.delete(restaurantId, id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int restaurantId, @Valid @RequestBody Dish dish, @PathVariable int id) {
        log.info("restaurant with id={} update {} with id={}", restaurantId, dish, id);
        service.update(restaurantId, dish, id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> create(@PathVariable int restaurantId, @Valid @RequestBody Dish dish) {
        Dish created = service.create(restaurantId, dish);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(DISH_URL + "/{id}")
                .buildAndExpand(created.getRestaurant().getId(), created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

}
