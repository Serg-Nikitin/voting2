package ru.nikitin.voting.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.nikitin.voting.model.Dish;
import ru.nikitin.voting.to.DishTo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Dish WHERE restaurant.id=:restaurantId AND id=:id")
    int delete(int restaurantId, int id);

    @Query("SELECT new ru.nikitin.voting.to.DishTo(d.id,d.name,d.price,d.servingDate,d.restaurant.id) FROM Dish d  where d.restaurant.id=:restaurantId AND d.id=:id")
    Optional<DishTo> findById(int restaurantId, int id);

    @Query("SELECT new ru.nikitin.voting.to.DishTo(d.id,d.name,d.price,d.servingDate,d.restaurant.id) FROM Dish d  where d.servingDate=:date GROUP BY d.servingDate, d.restaurant, d.id ORDER BY d.id")
    List<DishTo> getAllMenuByDate(LocalDate date);


    @Query("SELECT new ru.nikitin.voting.to.DishTo(d.id,d.name,d.price,d.servingDate,d.restaurant.id) FROM Dish d WHERE d.restaurant.id=:restaurantId AND d.servingDate=:date")
    List<DishTo> findMenuByRestaurantIdAndDate(int restaurantId, LocalDate date);

    @Query("SELECT new ru.nikitin.voting.to.DishTo(d.id,d.name,d.price,d.servingDate,d.restaurant.id) FROM Dish d WHERE d.restaurant.id=:restaurantId ORDER BY d.servingDate DESC")
    List<DishTo> findMenuByRestaurantId(int restaurantId);

    @Query("SELECT new ru.nikitin.voting.to.DishTo(d.id,d.name,d.price,d.servingDate,d.restaurant.id) FROM Dish d ORDER BY d.servingDate, d.restaurant.id")
    List<DishTo> findAllTo();


}
