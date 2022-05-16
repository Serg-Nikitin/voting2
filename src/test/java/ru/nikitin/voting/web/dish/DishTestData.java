package ru.nikitin.voting.web.dish;

import ru.nikitin.voting.model.Dish;
import ru.nikitin.voting.to.DishTo;
import ru.nikitin.voting.web.MatcherFactory;
import ru.nikitin.voting.web.restaurant.RestaurantTestData;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DishTestData {
    public static final MatcherFactory.Matcher<DishTo> DISH_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(DishTo.class);

    public static final int DISH_ID = 1;
    public static final int DISH_ID_29 = 29;

    public static final Dish dish1 = new Dish(DISH_ID, "Форель жаренная", LocalDate.of(2022, Month.APRIL, 20), 14500, RestaurantTestData.family);
    public static final Dish dish2 = new Dish(DISH_ID + 1, "Морская мелодия", LocalDate.of(2022, Month.APRIL, 20), 12000, RestaurantTestData.georgia);
    public static final Dish dish3 = new Dish(DISH_ID + 2, "Стейк", LocalDate.of(2022, Month.APRIL, 20), 35000, RestaurantTestData.odessa);
    public static final Dish dish4 = new Dish(DISH_ID + 3, "Лагман", LocalDate.of(2022, Month.APRIL, 20), 4400, RestaurantTestData.clouds);
    public static final Dish dish5 = new Dish(DISH_ID + 4, "Плов", LocalDate.of(2022, Month.APRIL, 20), 52000, RestaurantTestData.family);
    public static final Dish dish6 = new Dish(DISH_ID + 5, "Пельмени", LocalDate.of(2022, Month.APRIL, 20), 56900, RestaurantTestData.georgia);
    public static final Dish dish7 = new Dish(DISH_ID + 6, "Гуляш венгерский", LocalDate.of(2022, Month.APRIL, 20), 78000, RestaurantTestData.odessa);
    public static final Dish dish8 = new Dish(DISH_ID + 7, "Паста с баклажанами", LocalDate.of(2022, Month.APRIL, 20), 10000, RestaurantTestData.clouds);
    public static final Dish dish9 = new Dish(DISH_ID + 8, "Пицца", LocalDate.of(2022, Month.APRIL, 20), 18000, RestaurantTestData.family);
    public static final Dish dish10 = new Dish(DISH_ID + 9, "Салат Цезарь", LocalDate.of(2022, Month.APRIL, 20), 25000, RestaurantTestData.georgia);
    public static final Dish dish11 = new Dish(DISH_ID + 10, "Борщ", LocalDate.of(2022, Month.APRIL, 20), 36000, RestaurantTestData.odessa);
    public static final Dish dish12 = new Dish(DISH_ID + 11, "Мясо в жаровне", LocalDate.of(2022, Month.APRIL, 20), 46000, RestaurantTestData.clouds);
    public static final Dish dish13 = new Dish(DISH_ID + 12, "Шурпа", LocalDate.of(2022, Month.APRIL, 20), 16900, RestaurantTestData.family);
    public static final Dish dish14 = new Dish(DISH_ID + 13, "Утка по-пекински", LocalDate.of(2022, Month.APRIL, 20), 78000, RestaurantTestData.georgia);

    public static final Dish dish15 = new Dish(DISH_ID + 14, "Сёмга запеченная", LocalDate.of(2022, Month.APRIL, 21), 14500, RestaurantTestData.family);
    public static final Dish dish16 = new Dish(DISH_ID + 15, "Оливье", LocalDate.of(2022, Month.APRIL, 21), 12000, RestaurantTestData.georgia);
    public static final Dish dish17 = new Dish(DISH_ID + 16, "Цезарь", LocalDate.of(2022, Month.APRIL, 21), 35000, RestaurantTestData.odessa);
    public static final Dish dish18 = new Dish(DISH_ID + 17, "Солянка", LocalDate.of(2022, Month.APRIL, 21), 4400, RestaurantTestData.clouds);
    public static final Dish dish19 = new Dish(DISH_ID + 18, "Азу", LocalDate.of(2022, Month.APRIL, 21), 52000, RestaurantTestData.family);
    public static final Dish dish20 = new Dish(DISH_ID + 19, "Бешбармак", LocalDate.of(2022, Month.APRIL, 21), 56900, RestaurantTestData.georgia);
    public static final Dish dish21 = new Dish(DISH_ID + 20, "Пряный баклажан на гриле", LocalDate.of(2022, Month.APRIL, 21), 78000, RestaurantTestData.odessa);
    public static final Dish dish22 = new Dish(DISH_ID + 21, "Бургер с трюфелем", LocalDate.of(2022, Month.APRIL, 21), 10000, RestaurantTestData.clouds);
    public static final Dish dish23 = new Dish(DISH_ID + 22, "Стейк из форели", LocalDate.of(2022, Month.APRIL, 21), 18000, RestaurantTestData.family);
    public static final Dish dish24 = new Dish(DISH_ID + 23, "Салат Столичный", LocalDate.of(2022, Month.APRIL, 21), 25000, RestaurantTestData.georgia);
    public static final Dish dish25 = new Dish(DISH_ID + 24, "Уха", LocalDate.of(2022, Month.APRIL, 21), 36000, RestaurantTestData.odessa);
    public static final Dish dish26 = new Dish(DISH_ID + 25, "Соте", LocalDate.of(2022, Month.APRIL, 21), 46000, RestaurantTestData.clouds);
    public static final Dish dish27 = new Dish(DISH_ID + 26, "Ризотто", LocalDate.of(2022, Month.APRIL, 21), 16900, RestaurantTestData.family);
    public static final Dish dish28 = new Dish(DISH_ID + 27, "Царское блюдо", LocalDate.of(2022, Month.APRIL, 21), 78000, RestaurantTestData.georgia);

    public static final Dish dish29 = new Dish(DISH_ID + 28, "Оленина", LocalDate.now(), 59879, RestaurantTestData.family);
    public static final Dish dish30 = new Dish(DISH_ID + 29, "Жаркое", LocalDate.now(), 15989, RestaurantTestData.georgia);
    public static final Dish dish31 = new Dish(DISH_ID + 30, "Хаш", LocalDate.now(), 36412, RestaurantTestData.odessa);
    public static final Dish dish32 = new Dish(DISH_ID + 31, "Баранина", LocalDate.now(), 78954, RestaurantTestData.clouds);
    public static final Dish dish33 = new Dish(DISH_ID + 32, "Курник", LocalDate.now(), 78789, RestaurantTestData.family);
    public static final Dish dish34 = new Dish(DISH_ID + 33, "Омлет", LocalDate.now(), 13698, RestaurantTestData.georgia);

    public static List<Dish> dishes = Arrays.asList(dish1, dish2, dish3, dish4, dish5, dish6, dish7, dish8, dish9, dish10, dish11, dish12, dish13, dish14, dish15, dish16, dish17, dish18, dish19, dish20, dish21, dish22, dish23, dish24, dish25, dish26, dish27, dish28, dish29, dish30, dish31, dish32, dish33, dish34);
    public static Map<LocalDate, List<DishTo>> dishesRestaurant = dishes.stream()
            .filter(dish -> RestaurantTestData.RESTAURANT_ID.equals(dish.getRestaurant().getId()))
            .collect(Collectors.groupingBy(Dish::getDateOfServing, Collectors.mapping(DishTo::new, Collectors.toList())));

    public static Dish getNewDish() {
        return new Dish(null, "New", LocalDate.now(), 15000, RestaurantTestData.family);
    }

    public static Dish getUpdatedDish() {
        return new Dish(DISH_ID_29, "Updated", LocalDate.now(), 25000, RestaurantTestData.family);
    }
}
