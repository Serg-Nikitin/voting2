package ru.javaops.topjava2.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava2.model.Dish;

public interface DishRepository extends BaseRepository<Dish> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Dish WHERE restaurant.id=:restaurantId AND id=:id")
    int delete(int restaurantId, int id);

}
