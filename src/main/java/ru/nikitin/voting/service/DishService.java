package ru.nikitin.voting.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nikitin.voting.model.Dish;
import ru.nikitin.voting.model.Restaurant;
import ru.nikitin.voting.repository.DishRepository;
import ru.nikitin.voting.repository.RestaurantRepository;
import ru.nikitin.voting.to.DishTo;
import ru.nikitin.voting.to.Menu;

import java.time.LocalDate;
import java.util.List;

import static ru.nikitin.voting.util.ServiceUtil.*;

@Service
@Transactional(readOnly = true)
@Slf4j
@AllArgsConstructor
@CacheConfig(cacheNames = "dishes")
public class DishService {

    private final DishRepository repository;
    private final RestaurantRepository restaurantRepository;

    public DishTo get(Integer restaurantId, int id) {
        log.info("get restaurantId = {}, dishId = {}", restaurantId, id);
        return checkNotFound(repository.findById(id)
                .filter(dish -> restaurantId.equals(dish.getRestaurant().getId()))
                .map(DishTo::new), id);
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
    public void update(int restaurantId, DishTo dishTo, int id) {
        log.info("update restaurantId = {}, data = {}, dishId = {}", restaurantId, dishTo, id);
        checkDishBelongOldMenu(checkNotFound(repository.findById(id), id));
        Dish dish = new Dish(dishTo);
        Restaurant restaurant = restaurantRepository.getById(restaurantId);
        dish.setRestaurant(restaurant);
        repository.save(dish);
    }

    @CacheEvict(cacheNames = "dishes", allEntries = true)
    @Transactional
    public DishTo create(int restaurantId, DishTo dishTo) {
        log.info("crete restaurantId = {} data = {}", restaurantId, dishTo);
        Dish dish = new Dish(dishTo);
        dish.setRestaurant(restaurantRepository.getById(restaurantId));
        Dish returnDish = repository.save(dish);
        return new DishTo(returnDish);
    }

    public List<DishTo> getRestaurantMenuOnDate(int restaurantId, LocalDate date) {
        return null;
    }

    /**
     * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     */
    public List<Menu> getAllMenu(int restaurantId) {
        repository.findByRestaurantId(restaurantId);
        return null;
    }
}
