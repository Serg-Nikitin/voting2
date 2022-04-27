package ru.javaops.topjava2.web.dish;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.javaops.topjava2.model.Dish;
import ru.javaops.topjava2.repository.DishRepository;
import ru.javaops.topjava2.web.AbstractEntityBaseController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = DishAdminRestController.DISH_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class DishAdminRestController extends AbstractEntityBaseController<Dish> {

    public static final String DISH_URL = "/api/admin/dishes";

    @Autowired
    DishRepository repository;


    @GetMapping("/{id}")
    public ResponseEntity<Dish> get(@PathVariable int id) {
        return super.get(id, repository);
    }

    @GetMapping
    public List<Dish> getAll() {
        return super.getAll(repository);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id, repository);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> create(@Valid @RequestBody Dish dish) {
        return super.create(dish, DISH_URL, repository);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Dish dish, @PathVariable int id) {
        super.update(dish, id, repository);
    }


}
