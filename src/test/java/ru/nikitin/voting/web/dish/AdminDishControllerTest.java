package ru.nikitin.voting.web.dish;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.nikitin.voting.service.DishService;
import ru.nikitin.voting.to.DishTo;
import ru.nikitin.voting.util.JsonUtil;
import ru.nikitin.voting.web.AbstractControllerTest;
import ru.nikitin.voting.web.restaurant.RestaurantTestData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.nikitin.voting.web.user.UserTestData.ADMIN_MAIL;

@Slf4j
class AdminDishControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminDishController.DISH_URL + '/';

    @Autowired
    private DishService service;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    public void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + DishTestData.DISH_ID, RestaurantTestData.RESTAURANT_ID))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DishTestData.DISH_TO_MATCHER.contentJson(new DishTo(DishTestData.dish1)));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    public void getAllMenu() throws Exception {
        ResultActions action = perform(MockMvcRequestBuilders.get(REST_URL, RestaurantTestData.RESTAURANT_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        String actual = action.andReturn().getResponse().getContentAsString();
        Assertions.assertEquals(actual, JsonUtil.writeValue(DishTestData.dishesRestaurant));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    public void update() throws Exception {
        DishTo updated = new DishTo(DishTestData.getUpdatedDish());
        perform(MockMvcRequestBuilders.put(REST_URL + DishTestData.DISH_ID_29, RestaurantTestData.RESTAURANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());
        DishTestData.DISH_TO_MATCHER.assertMatch(service.get(RestaurantTestData.RESTAURANT_ID, DishTestData.DISH_ID_29), new DishTo(DishTestData.getUpdatedDish()));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    public void create() throws Exception {
        DishTo newDish = new DishTo(DishTestData.getNewDish());
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL, RestaurantTestData.RESTAURANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish)))
                .andExpect(status().isCreated());
        DishTo created = DishTestData.DISH_TO_MATCHER.readFromJson(action);
        Integer newId = created.getId();
        newDish.setId(newId);
        DishTestData.DISH_TO_MATCHER.assertMatch(created, newDish);
        DishTestData.DISH_TO_MATCHER.assertMatch(service.get(RestaurantTestData.RESTAURANT_ID, newId), newDish);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    public void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + DishTestData.DISH_ID, RestaurantTestData.RESTAURANT_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}