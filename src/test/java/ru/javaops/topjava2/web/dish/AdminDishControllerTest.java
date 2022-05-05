package ru.javaops.topjava2.web.dish;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.topjava2.error.IllegalRequestDataException;
import ru.javaops.topjava2.model.Dish;
import ru.javaops.topjava2.service.DishService;
import ru.javaops.topjava2.util.JsonUtil;
import ru.javaops.topjava2.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.topjava2.web.dish.DishTestData.*;
import static ru.javaops.topjava2.web.restaurant.RestaurantTestData.RESTAURANT_ID;
import static ru.javaops.topjava2.web.user.UserTestData.ADMIN_MAIL;

@Slf4j
class AdminDishControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminDishController.DISH_URL + '/';

    @Autowired
    private DishService service;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    public void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + DISH_ID, RESTAURANT_ID))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(dish1));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    public void getAllMenu() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL, RESTAURANT_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(dishesRestaurant));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    public void update() throws Exception {
        Dish updated = getUpdatedDish();
        perform(MockMvcRequestBuilders.put(REST_URL + DISH_ID, RESTAURANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());
        String msg = String.format("test, exception restaurantId=%d, dishId=%d", RESTAURANT_ID, DISH_ID);
        DISH_MATCHER.assertMatch(service.get(RESTAURANT_ID, DISH_ID)
                .orElseThrow(() -> new IllegalRequestDataException(msg)), getUpdatedDish());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    public void create() throws Exception {
        Dish newDish = getNewDish();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL, RESTAURANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish)))
                .andExpect(status().isCreated());
        Dish created = DISH_MATCHER.readFromJson(action);
        Integer newId = created.getId();
        newDish.setId(newId);
        DISH_MATCHER.assertMatch(created, newDish);
        String msg = String.format("test, exception restaurantId=%d, dishId=%d", RESTAURANT_ID, DISH_ID);
        DISH_MATCHER.assertMatch(service.get(RESTAURANT_ID, newId)
                .orElseThrow(() -> new IllegalRequestDataException(msg)), newDish);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    public void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + DISH_ID, RESTAURANT_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}