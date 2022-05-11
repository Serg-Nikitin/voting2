package ru.javaops.topjava2.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava2.error.IllegalRequestDataException;
import ru.javaops.topjava2.model.Dish;
import ru.javaops.topjava2.repository.DishRepository;
import ru.javaops.topjava2.repository.RestaurantRepository;
import ru.javaops.topjava2.to.DishTo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        return repository.findById(id)
                .filter(dish -> restaurantId.equals(dish.getRestaurant().getId()))
                .map(DishTo::new).orElseThrow(() -> new IllegalRequestDataException(String.format("Restaurant with id=%d don't have dish with id =%d", restaurantId, id)));
    }

    public Map<LocalDate, List<DishTo>> getAllMenu(int restaurantId) {
        return findAll().stream()
                .filter(dish -> dish.getRestaurant().id() == restaurantId)
                .collect(Collectors.groupingBy(Dish::getDateOfServing, Collectors.mapping(DishTo::new, Collectors.toList())));
    }

    @Cacheable("dishes")
    public List<Dish> findAll() {
        return repository.findAll(Sort.by("dateOfServing", "restaurant"));
    }

    @CacheEvict(cacheNames = "dishes", allEntries = true)
    public void delete(int restaurantId, int id) {
        checkAffiliation(restaurantId, id, repository.delete(restaurantId, id) == 0);
    }

    @CacheEvict(cacheNames = "dishes", allEntries = true)
    @Transactional
    public void update(Integer restaurantId, DishTo dishTo, int id) {
        assureIdConsistent(dishTo, id);
        Dish dish = new Dish(dishTo);
        dish.setRestaurant(restaurantRepository.getById(restaurantId));
        repository.save(dish);
    }

    @CacheEvict(cacheNames = "dishes", allEntries = true)
    @Transactional
    public DishTo create(int restaurantId, DishTo dishTo) {
        checkNew(dishTo);
        Dish dish = new Dish(dishTo);
        dish.setRestaurant(restaurantRepository.getById(restaurantId));
        Dish returnDish = repository.save(dish);
        return new DishTo(returnDish);
    }
}
