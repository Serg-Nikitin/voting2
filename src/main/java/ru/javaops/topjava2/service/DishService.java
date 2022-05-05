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

import java.util.List;
import java.util.Optional;

import static ru.javaops.topjava2.util.DishUtil.checkAffiliation;
import static ru.javaops.topjava2.util.validation.ValidationUtil.assureIdConsistent;
import static ru.javaops.topjava2.util.validation.ValidationUtil.checkNew;

@Service
@Transactional(readOnly = true)
@Slf4j
@CacheConfig(cacheNames = "dish")
public class DishService {

    private final DishRepository repository;
    private final RestaurantRepository restaurantRepository;

    public DishService(DishRepository repository, RestaurantRepository restaurantRepository) {
        this.repository = repository;
        this.restaurantRepository = restaurantRepository;
    }

    public Optional<Dish> get(Integer restaurantId, Integer id) {
        return repository.findById(id)
                .filter(dish -> restaurantId.equals(dish.getRestaurant().getId()));
    }

    @Cacheable
    public List<Dish> getAll() {
        return repository.findAll(Sort.by("dateOfServing", "name"));
    }

    @CacheEvict(allEntries = true)
    public void delete(int restaurantId, int id) {
        checkAffiliation(restaurantId, id, repository.delete(restaurantId, id) == 0);
    }

    @CacheEvict(allEntries = true)
    @Transactional
    public void update(Integer restaurantId, Dish dish, int id) {
        assureIdConsistent(dish, id);
        boolean check = get(restaurantId, id).isEmpty();
        checkAffiliation(restaurantId, id, check);
        repository.save(dish);
    }

    @CacheEvict(allEntries = true)
    @Transactional
    public Dish create(int restaurantId, Dish dish) {
        checkNew(dish);
        dish.setRestaurant(restaurantRepository.getById(restaurantId));
        return repository.save(dish);
    }
}
