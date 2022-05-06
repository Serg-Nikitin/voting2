package ru.javaops.topjava2.web.dish;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.topjava2.service.DishService;
import ru.javaops.topjava2.to.DishTo;
import ru.javaops.topjava2.util.JsonUtil;
import ru.javaops.topjava2.web.AbstractControllerTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
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
                .andExpect(DISH_TO_MATCHER.contentJson(new DishTo(dish1)));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    public void getAllMenu() throws Exception {
        ResultActions action = perform(MockMvcRequestBuilders.get(REST_URL, RESTAURANT_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        Map<LocalDate, List<DishTo>> map = JsonUtil.readMapValues(action.andReturn().getResponse().getContentAsString());
        assertTrue(map.equals(dishesRestaurant));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    public void update() throws Exception {
        DishTo updated = new DishTo(getUpdatedDish());
        perform(MockMvcRequestBuilders.put(REST_URL + DISH_ID, RESTAURANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());
        String msg = String.format("test, exception restaurantId=%d, dishId=%d", RESTAURANT_ID, DISH_ID);
        DISH_TO_MATCHER.assertMatch(service.get(RESTAURANT_ID, DISH_ID), new DishTo(getUpdatedDish()));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    public void create() throws Exception {
        DishTo newDish = new DishTo(getNewDish());
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL, RESTAURANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish)))
                .andExpect(status().isCreated());
        DishTo created = DISH_TO_MATCHER.readFromJson(action);
        Integer newId = created.getId();
        newDish.setId(newId);
        DISH_TO_MATCHER.assertMatch(created, newDish);
        String msg = String.format("test, exception restaurantId=%d, dishId=%d", RESTAURANT_ID, DISH_ID);
        DISH_TO_MATCHER.assertMatch(service.get(RESTAURANT_ID, newId), newDish);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    public void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + DISH_ID, RESTAURANT_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}