package ru.nikitin.voting.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nikitin.voting.model.Restaurant;
import ru.nikitin.voting.repository.RestaurantRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Transactional(readOnly = true)
@Slf4j
@CacheConfig(cacheNames = "restaurants")
public class RestaurantService {

    private final RestaurantRepository repository;

    public RestaurantService(RestaurantRepository repository) {
        this.repository = repository;
    }

    public Restaurant findById(int id) {
        log.info("findById with id = {}", id);
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Restaurant with id = %d not found", id)));
    }

    public Restaurant getById(int id) {
        log.info("findById with id = {}", id);
        return repository.getById(id);
    }

    @Cacheable(cacheNames = {"restaurants"})
    public List<Restaurant> findAll() {
        log.info("getAll");
        return repository.findAll();
    }

    @CacheEvict(cacheNames = {"restaurants"}, allEntries = true)
    public void deleteExisted(int id) {
        log.info("deleteExisted id = {}", id);
        repository.deleteExisted(id);
    }

    @CacheEvict(cacheNames = {"restaurants"}, allEntries = true)
    @Transactional
    public Restaurant save(Restaurant restaurant) {
        log.info("save restaurant = {}", restaurant);
        return repository.save(restaurant);
    }
}
