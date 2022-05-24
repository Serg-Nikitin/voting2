package ru.nikitin.voting.util;

import ru.nikitin.voting.error.IllegalRequestDataException;
import ru.nikitin.voting.model.Vote;
import ru.nikitin.voting.to.DishTo;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public class ServiceUtil {

    public static void checkAffiliation(int restaurantId, int id, boolean check) {
        if (check) {
            throw new IllegalRequestDataException(String.format("dish with id=%d does not belong menu to the restaurant with id=%d", id, restaurantId));
        }
    }

    public static void checkDishBelongOldMenu(DishTo dish) {
        LocalDate today = LocalDate.now();
        if (today.isAfter(dish.getServingDate())) {
            throw new IllegalRequestDataException(String.format("It is forbidden update restaurant's dish from the old menu, date = %s", today));
        }
    }

    public static <T> T checkNotFound(Optional<T> optional, int id) {
        return optional.orElseThrow(() -> new EntityNotFoundException(String.format("Entity with id = %d not found", id)));
    }

    public static boolean checkTime() {
        LocalTime now = LocalTime.now();
        LocalTime to = LocalTime.of(11, 0);
        return !now.isAfter(to);
    }

    public static boolean checkDate(Optional<Vote> to) {
        LocalDate date = LocalDate.now();
        return date.isEqual(to.get().getVoteDate());
    }
}
