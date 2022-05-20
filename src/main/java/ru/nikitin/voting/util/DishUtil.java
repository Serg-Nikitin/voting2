package ru.nikitin.voting.util;

import ru.nikitin.voting.error.IllegalRequestDataException;
import ru.nikitin.voting.model.Dish;
import ru.nikitin.voting.model.Restaurant;
import ru.nikitin.voting.to.DishTo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DishUtil {

    public static void checkAffiliation(int restaurantId, int id, boolean check) {
        if (check) {
            throw new IllegalRequestDataException(String.format("dish with id=%d does not belong menu to the restaurant with id=%d", id, restaurantId));
        }
    }

    public static void checkDishBelongOldMenu(Dish dish) {
        LocalDate date = LocalDate.now();
        if (date.compareTo(dish.getServingDate()) > 0) {
            throw new IllegalRequestDataException(String.format("It is forbidden update restaurant's dish from the old menu, date = %s", date));
        }
    }

    public static Double convert(Integer value) {
        Double result = value.doubleValue() / 100;
        return result;
    }

    public static Integer convert(Double value) {
        Double result = value * 100;
        return result.intValue();
    }

    public static Map<Restaurant, List<DishTo>> convert(Map<Restaurant, Map<LocalDate, List<DishTo>>> map, LocalDate date) {
        Map<Restaurant, List<DishTo>> menu = new HashMap<>();
        for (Map.Entry<Restaurant, Map<LocalDate, List<DishTo>>> element : map.entrySet()) {
            menu.put(element.getKey(), element.getValue().get(date));
        }
        return menu;
    }

    public static LocalDate convert(String value) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(value, formatter);
    }
}
