package ru.javaops.topjava2.web.dish;

import org.checkerframework.checker.units.qual.C;
import ru.javaops.topjava2.model.Dish;
import ru.javaops.topjava2.to.DishTo;
import ru.javaops.topjava2.web.MatcherFactory;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.javaops.topjava2.web.restaurant.RestaurantTestData.*;

public class DishTestData {
    public static final MatcherFactory.Matcher<DishTo> DISH_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(DishTo.class);

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

    public static final Dish dish15 = new Dish(DISH_ID + 14, "Сёмга запеченная", LocalDate.of(2022, Month.APRIL, 21), 14500, family);
    public static final Dish dish16 = new Dish(DISH_ID + 15, "Оливье", LocalDate.of(2022, Month.APRIL, 21), 12000, georgia);
    public static final Dish dish17 = new Dish(DISH_ID + 16, "Цезарь", LocalDate.of(2022, Month.APRIL, 21), 35000, odessa);
    public static final Dish dish18 = new Dish(DISH_ID + 17, "Солянка", LocalDate.of(2022, Month.APRIL, 21), 4400, clouds);
    public static final Dish dish19 = new Dish(DISH_ID + 18, "Азу", LocalDate.of(2022, Month.APRIL, 21), 52000, family);
    public static final Dish dish20 = new Dish(DISH_ID + 19, "Бешбармак", LocalDate.of(2022, Month.APRIL, 21), 56900, georgia);
    public static final Dish dish21 = new Dish(DISH_ID + 20, "Пряный баклажан на гриле", LocalDate.of(2022, Month.APRIL, 21), 78000, odessa);
    public static final Dish dish22 = new Dish(DISH_ID + 21, "Бургер с трюфелем", LocalDate.of(2022, Month.APRIL, 21), 10000, clouds);
    public static final Dish dish23 = new Dish(DISH_ID + 22, "Стейк из форели", LocalDate.of(2022, Month.APRIL, 21), 18000, family);
    public static final Dish dish24 = new Dish(DISH_ID + 23, "Салат Столичный", LocalDate.of(2022, Month.APRIL, 21), 25000, georgia);
    public static final Dish dish25 = new Dish(DISH_ID + 24, "Уха", LocalDate.of(2022, Month.APRIL, 21), 36000, odessa);
    public static final Dish dish26 = new Dish(DISH_ID + 25, "Соте", LocalDate.of(2022, Month.APRIL, 21), 46000, clouds);
    public static final Dish dish27 = new Dish(DISH_ID + 26, "Ризотто", LocalDate.of(2022, Month.APRIL, 21), 16900, family);
    public static final Dish dish28 = new Dish(DISH_ID + 27, "Царское блюдо", LocalDate.of(2022, Month.APRIL, 21), 78000, georgia);


    public static List<Dish> dishes = Arrays.asList(dish1, dish2, dish3, dish4, dish5, dish6, dish7, dish8, dish9, dish10, dish11, dish12, dish13, dish14, dish15, dish16, dish17, dish18, dish19, dish20, dish21, dish22, dish23, dish24, dish25, dish26, dish27, dish28);
    public static Map<LocalDate,List<DishTo>> dishesRestaurant = dishes.stream()
            .filter(dish -> RESTAURANT_ID.equals(dish.getRestaurant().getId()))
            .collect(Collectors.groupingBy(Dish::getDateOfServing, Collectors.mapping(DishTo::new,Collectors.toList())));

    public static List<Dish> getMenu(Integer restaurantId){
        return dishes.stream().filter(dish -> restaurantId.equals(dish.getRestaurant().getId())).sorted(Comparator.comparing(Dish::getDateOfServing).thenComparing(Dish::getId)).collect(Collectors.toList());

    }

    public static Dish getNewDish() {
        return new Dish(null, "New", LocalDate.now(), 15000, family);
    }

    public static Dish getUpdatedDish() {
        return new Dish(DISH_ID, "Updated", LocalDate.now(), 25000, family);
    }

}
