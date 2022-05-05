package ru.javaops.topjava2.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava2.error.NotFoundException;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.repository.RestaurantRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@Slf4j
@CacheConfig(cacheNames = "restaurant")
public class RestaurantService {

    private final RestaurantRepository repository;

    public RestaurantService(RestaurantRepository repository) {
        this.repository = repository;
    }


    public Optional<Restaurant> findById(int id) {
        return repository.findById(id);
    }

    public Restaurant getById(int id) {
        return findAll().stream()
                .filter(r -> r.id() == id)
                .findFirst()
                .orElseThrow(() -> new NotFoundException(String.format("Restaurant with id=%d not found", id)));
    }

    @Cacheable
    public List<Restaurant> findAll() {
        log.info("getAll");
        return repository.findAll();
    }

    @CacheEvict(allEntries = true)
    public void deleteExisted(int id) {
        repository.deleteExisted(id);
    }

    @CacheEvict(allEntries = true)
    public Restaurant save(Restaurant restaurant) {
        return repository.save(restaurant);
    }
}
