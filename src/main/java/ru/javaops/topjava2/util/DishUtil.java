package ru.javaops.topjava2.util;

import ru.javaops.topjava2.error.IllegalRequestDataException;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.to.DishTo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DishUtil {

    public static void checkAffiliation(int restaurantId, int id, boolean check) {
        String msg = String.format("dish with id=%d does not belong menu to the restaurant with id=%d", id, restaurantId);
        if (check) {
            throw new IllegalRequestDataException(msg);
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
        LocalDate date = LocalDate.parse(value, formatter);
        return date;
    }

    public static void main(String[] args) {
        LocalDate date = convert("2022-04-20");
        System.out.println("Local Date ++ " + date);
    }

}