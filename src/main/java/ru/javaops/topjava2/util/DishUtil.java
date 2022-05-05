package ru.javaops.topjava2.util;

import ru.javaops.topjava2.error.IllegalRequestDataException;

public class DishUtil {

    public static void checkAffiliation(int restaurantId, int id, boolean check) {
        String msg = String.format("dish with id=%d does not belong menu to the restaurant with id=%d", id, restaurantId);
        if (check) {
            throw new IllegalRequestDataException(msg);
        }
    }
}
