package ru.nikitin.voting.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nikitin.voting.model.Restaurant;
import ru.nikitin.voting.repository.RestaurantRepository;

import java.util.List;

import static ru.nikitin.voting.util.ServiceUtil.checkNotFound;

@Service
@Slf4j
@AllArgsConstructor
public class RestaurantService {

    private final RestaurantRepository repository;

    public Restaurant findById(int id) {
        log.info("findById with id = {}", id);
        return checkNotFound(repository.findById(id), id);
    }

    public List<Restaurant> findAll() {
        log.info("getAll");
        return repository.findAll();
    }

    public void deleteExisted(int id) {
        log.info("deleteExisted id = {}", id);
        repository.deleteExisted(id);
    }


    public Restaurant save(Restaurant restaurant) {
        log.info("save restaurant = {}", restaurant);
        return repository.save(restaurant);
    }
}
