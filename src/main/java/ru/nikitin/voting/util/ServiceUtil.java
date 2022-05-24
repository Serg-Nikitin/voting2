package ru.nikitin.voting.util;

import ru.nikitin.voting.error.IllegalRequestDataException;
import ru.nikitin.voting.model.Restaurant;
import ru.nikitin.voting.to.DishTo;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public static Map<Restaurant, List<DishTo>> convert(Map<Restaurant, Map<LocalDate, List<DishTo>>> map, LocalDate date) {
        Map<Restaurant, List<DishTo>> menu = new HashMap<>();
        for (Map.Entry<Restaurant, Map<LocalDate, List<DishTo>>> element : map.entrySet()) {
            menu.put(element.getKey(), element.getValue().get(date));
        }
        return menu;
    }

    public static <T> T checkNotFound(Optional<T> optional, int id) {
        return optional.orElseThrow(() -> new EntityNotFoundException(String.format("Entity with id = %d not found", id)));
    }

    /**
     * !!!!!!!!!!!!!!!!!!!!!!!!!
     */
    public static LocalDate convert(String value) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(value, formatter);
    }


}