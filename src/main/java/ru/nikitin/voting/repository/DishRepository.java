package ru.nikitin.voting.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.nikitin.voting.model.Dish;

public interface DishRepository extends BaseRepository<Dish> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Dish WHERE restaurant.id=:restaurantId AND id=:id")
    int delete(int restaurantId, int id);

}
