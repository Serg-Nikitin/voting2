package ru.nikitin.voting.web.dish;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.nikitin.voting.service.DishService;
import ru.nikitin.voting.to.DishTo;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.nikitin.voting.util.ServiceUtil.checkAffiliation;
import static ru.nikitin.voting.util.ServiceUtil.checkDishBelongOldMenu;
import static ru.nikitin.voting.util.validation.ValidationUtil.assureIdConsistent;
import static ru.nikitin.voting.util.validation.ValidationUtil.checkNew;

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
    public List<DishTo> getAllMenu(@PathVariable Integer restaurantId) {
        log.info("getAllMenu restaurantId = {}", restaurantId);
        return service.getAllByRestaurantId(restaurantId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int restaurantId, @PathVariable int id) {
        log.info("delete with id={} and restaurantId={}", id, restaurantId);
        checkAffiliation(restaurantId, id, service.delete(restaurantId, id) == 0);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int restaurantId, @Valid @RequestBody DishTo dishTo, @PathVariable int id) {
        assureIdConsistent(dishTo, id);
        checkAffiliation(restaurantId, id, dishTo.getRestaurantId() != restaurantId);
        checkDishBelongOldMenu(dishTo);
        log.info("update with id={} restaurantId = {} and data ={}", id, restaurantId, dishTo);
        service.update(restaurantId, dishTo, id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DishTo> create(@PathVariable int restaurantId, @Valid @RequestBody DishTo dishTo) {
        checkNew(dishTo);
        log.info("create dish with restaurantId = {}, data = {}", restaurantId, dishTo);
        DishTo created = service.create(restaurantId, dishTo);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(DISH_URL + "/{id}")
                .buildAndExpand(restaurantId, created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping("/byDate")
    public List<DishTo> getMenuByRestaurantIdAndDate(@PathVariable int restaurantId, @RequestParam LocalDate date) {
        log.info("getAllMenuOnDate restaurantId = {}, date = {}", restaurantId, date);
        return service.getMenuByRestaurantIdAndDate(restaurantId, date);
    }
}
