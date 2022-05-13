package ru.javaops.topjava2.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava2.model.Dish;
import ru.javaops.topjava2.repository.DishRepository;
import ru.javaops.topjava2.repository.RestaurantRepository;
import ru.javaops.topjava2.to.DishTo;
import ru.javaops.topjava2.util.DishUtil;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static ru.javaops.topjava2.util.DishUtil.checkAffiliation;
import static ru.javaops.topjava2.util.validation.ValidationUtil.assureIdConsistent;
import static ru.javaops.topjava2.util.validation.ValidationUtil.checkNew;

@Service
@Transactional(readOnly = true)
@Slf4j
@CacheConfig(cacheNames = "dishes")
public class DishService {

    private final DishRepository repository;
    private final RestaurantRepository restaurantRepository;

    public DishService(DishRepository repository, RestaurantRepository restaurantRepository) {
        this.repository = repository;
        this.restaurantRepository = restaurantRepository;
    }

    public DishTo get(Integer restaurantId, Integer id) {
        log.info("get restaurantId = {}, dishId = {}", restaurantId, id);
        return repository.findById(id)
                .filter(dish -> restaurantId.equals(dish.getRestaurant().getId()))
                .map(DishTo::new).orElseThrow(() -> new EntityNotFoundException(String.format("Restaurant with id=%d don't have dish with id =%d", restaurantId, id)));
    }

    @Cacheable({"dishes"})
    public List<Dish> findAll() {
        log.info("findAll");
        return repository.findAll(Sort.by("dateOfServing", "restaurant"));
    }

    @CacheEvict(cacheNames = "dishes", allEntries = true)
    public void delete(int restaurantId, int id) {
        log.info("delete restaurantId = {}, dishId = {}", restaurantId, id);
        checkAffiliation(restaurantId, id, repository.delete(restaurantId, id) == 0);
    }

    @CacheEvict(cacheNames = "dishes", allEntries = true)
    @Transactional
    public void update(Integer restaurantId, DishTo dishTo, int id) {
        log.info("update restaurantId = {}, data = {}, dishId = {}", restaurantId, dishTo, id);
        assureIdConsistent(dishTo, id);
        DishUtil.checkDishBelongOldMenu(repository.getById(id));
        Dish dish = new Dish(dishTo);
        dish.setRestaurant(restaurantRepository.getById(restaurantId));
        repository.save(dish);
    }

    @CacheEvict(cacheNames = "dishes", allEntries = true)
    @Transactional
    public DishTo create(int restaurantId, DishTo dishTo) {
        log.info("crete restaurantId = {} data = {}", restaurantId, dishTo);
        checkNew(dishTo);
        Dish dish = new Dish(dishTo);
        dish.setRestaurant(restaurantRepository.getById(restaurantId));
        Dish returnDish = repository.save(dish);
        return new DishTo(returnDish);
    }
}
