package ru.nikitin.voting.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nikitin.voting.model.Dish;
import ru.nikitin.voting.model.Restaurant;
import ru.nikitin.voting.repository.DishRepository;
import ru.nikitin.voting.repository.RestaurantRepository;
import ru.nikitin.voting.to.DishTo;

import java.time.LocalDate;
import java.util.List;

import static ru.nikitin.voting.to.DishTo.getDishTo;
import static ru.nikitin.voting.util.ServiceUtil.checkNotFound;

@Service
@Transactional(readOnly = true)
@Slf4j
@AllArgsConstructor
@CacheConfig(cacheNames = "dishes")
public class DishService {

    private final DishRepository repository;
    private final RestaurantRepository restaurantRepository;

    public DishTo get(int restaurantId, int id) {
        log.info("get restaurantId = {}, dishId = {}", restaurantId, id);
        return checkNotFound(repository.findById(restaurantId, id), id);
    }

    @Cacheable({"dishes"})
    public List<DishTo> findAll() {
        log.info("findAll");
        return repository.findAllTo();
    }

    @CacheEvict(cacheNames = "dishes", allEntries = true)
    public int delete(int restaurantId, int id) {
        log.info("delete restaurantId = {}, dishId = {}", restaurantId, id);
        return repository.delete(restaurantId, id);
    }

    @CacheEvict(cacheNames = "dishes", allEntries = true)
    @Transactional
    public void update(int restaurantId, DishTo dishTo, int id) {
        log.info("update restaurantId = {}, data = {}, dishId = {}", restaurantId, dishTo, id);
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
        return getDishTo(returnDish);
    }

    public List<DishTo> getAllByRestaurantId(int restaurantId) {
        return repository.findMenuByRestaurantId(restaurantId);
    }

    public List<DishTo> getMenuByRestaurantIdAndDate(int restaurantId, LocalDate date) {
        return repository.findMenuByRestaurantIdAndDate(restaurantId, date);
    }

    /**
     * for voting
     */
    public List<DishTo> getAllByDate(LocalDate date) {
        return repository.getAllMenuByDate(date);
    }


}
