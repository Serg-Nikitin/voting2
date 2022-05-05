package ru.javaops.topjava2.service;

import org.springframework.stereotype.Service;
import ru.javaops.topjava2.error.IllegalRequestDataException;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.model.User;
import ru.javaops.topjava2.model.Vote;
import ru.javaops.topjava2.repository.UserRepository;
import ru.javaops.topjava2.repository.VoteRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class VoteService {

    private final VoteRepository repository;
    private final RestaurantService service;
    private final UserRepository userRepository;

    public VoteService(VoteRepository repository, RestaurantService service, UserRepository userRepository) {
        this.repository = repository;
        this.service = service;
        this.userRepository = userRepository;
    }

    public Vote voting(int userId, int restaurantId) {
        LocalDateTime now = LocalDateTime.now();
        List<Integer> listId = repository.getLastVoteToday(userId, now.toLocalDate());
        Restaurant restaurant = service.getById(restaurantId);
        User user = userRepository.getById(userId);
        if (listId.isEmpty()) {
            return create(user, restaurant, now.toLocalDate());
        } else if (now.toLocalTime().compareTo(LocalTime.of(11, 0)) <= 0) {
            return update(user, restaurant, listId.get(2), now.toLocalDate());
        } else {
            throw new IllegalRequestDataException("You can't change your vote after 11:00");
        }
    }

    public Vote create(User user, Restaurant restaurant, LocalDate nowDate) {
        return repository.save(new Vote(nowDate, restaurant, user, true));
    }

    public Vote update(User user, Restaurant restaurant, int id, LocalDate nowDate) {
        return repository.save(new Vote(id, nowDate, restaurant, user, true));
    }
}
