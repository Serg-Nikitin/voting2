package ru.javaops.topjava2.web.restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.repository.RestaurantRepository;
import ru.javaops.topjava2.web.AbstractEntityBaseController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = RestaurantAdminController.RESTAURANT_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantAdminController extends AbstractEntityBaseController<Restaurant> {

    public static final String RESTAURANT_URL = "/api/admin/restaurants";

    @Autowired
    protected RestaurantRepository repository;

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> get(@PathVariable int id) {
        return super.get(id, repository);
    }

    @GetMapping
    public List<Restaurant> getAll() {
        return super.getAll(repository);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id, repository);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> create(@Valid @RequestBody Restaurant restaurant) {
        return super.create(restaurant, RESTAURANT_URL, repository);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Restaurant restaurant, @PathVariable int id) {
        super.update(restaurant, id, repository);
    }
}
