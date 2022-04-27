package ru.javaops.topjava2.web.dish;

import ru.javaops.topjava2.model.Dish;
import ru.javaops.topjava2.web.MatcherFactory;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static ru.javaops.topjava2.web.restaurant.RestaurantTestData.*;

public class DishTestData {
    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Dish.class, "restaurant");

    public static final int DISH_ID = 1;

    public static final Dish dish1 = new Dish(DISH_ID, "Форель жаренная", LocalDate.of(2022, Month.APRIL, 20), 14500, family);
    public static final Dish dish2 = new Dish(DISH_ID + 1, "Морская мелодия", LocalDate.of(2022, Month.APRIL, 20), 12000, georgia);
    public static final Dish dish3 = new Dish(DISH_ID + 2, "Стейк", LocalDate.of(2022, Month.APRIL, 20), 35000, odessa);
    public static final Dish dish4 = new Dish(DISH_ID + 3, "Лагман", LocalDate.of(2022, Month.APRIL, 20), 4400, clouds);
    public static final Dish dish5 = new Dish(DISH_ID + 4, "Плов", LocalDate.of(2022, Month.APRIL, 20), 52000, family);
    public static final Dish dish6 = new Dish(DISH_ID + 5, "Пельмени", LocalDate.of(2022, Month.APRIL, 20), 56900, georgia);
    public static final Dish dish7 = new Dish(DISH_ID + 6, "Гуляш венгерский", LocalDate.of(2022, Month.APRIL, 20), 78000, odessa);
    public static final Dish dish8 = new Dish(DISH_ID + 7, "Паста с баклажанами", LocalDate.of(2022, Month.APRIL, 20), 10000, clouds);
    public static final Dish dish9 = new Dish(DISH_ID + 8, "Пицца", LocalDate.of(2022, Month.APRIL, 20), 18000, family);
    public static final Dish dish10 = new Dish(DISH_ID + 9, "Салат Цезарь", LocalDate.of(2022, Month.APRIL, 20), 25000, georgia);
    public static final Dish dish11 = new Dish(DISH_ID + 10, "Борщ", LocalDate.of(2022, Month.APRIL, 20), 36000, odessa);
    public static final Dish dish12 = new Dish(DISH_ID + 11, "Мясо в жаровне", LocalDate.of(2022, Month.APRIL, 20), 46000, clouds);
    public static final Dish dish13 = new Dish(DISH_ID + 12, "Шурпа", LocalDate.of(2022, Month.APRIL, 20), 16900, family);
    public static final Dish dish14 = new Dish(DISH_ID + 13, "Утка по-пекински", LocalDate.of(2022, Month.APRIL, 20), 78000, georgia);

    public static List<Dish> dishes = Arrays.asList(dish1, dish2, dish3, dish4, dish5, dish6, dish7, dish8, dish9, dish10, dish11, dish12, dish13, dish14);

    public static Dish getNewDish() {
        return new Dish(null, "New", LocalDate.now(), 15000, family);
    }

    public static Dish getUpdatedDish() {
        return new Dish(DISH_ID, "Updated", LocalDate.now(), 25000, georgia);
    }


}
