package ru.nikitin.voting.web.restaurant;

import ru.nikitin.voting.model.Address;
import ru.nikitin.voting.model.Restaurant;
import ru.nikitin.voting.web.MatcherFactory;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class);

    public static final Integer RESTAURANT_ID = 1;

    public static final Restaurant family = new Restaurant(RESTAURANT_ID, "Семейный", new Address("Россия", "Пермский край", "Пермь", "Ленина", 30));
    public static final Restaurant georgia = new Restaurant(RESTAURANT_ID + 1, "Georgia", new Address("Россия", "Пермский край", "Пермь", "Орджоникидзе", 18));
    public static final Restaurant odessa = new Restaurant(RESTAURANT_ID + 2, "Одесса", new Address("Россия", "Пермский край", "Пермь", "Белинского", 25));
    public static final Restaurant clouds = new Restaurant(RESTAURANT_ID + 3, "Облака", new Address("Россия", "Пермский край", "Пермь", "Екатерининская", 120));

    public static Restaurant getNewRestaurant() {
        return new Restaurant(null, "New", new Address("New", "New", "New", "New", 0));
    }

    public static Restaurant getUpdatedRestaurant() {
        return new Restaurant(RESTAURANT_ID, "Updated", new Address("Updated", "Updated", "Updated", "Updated", 1));
    }
}
