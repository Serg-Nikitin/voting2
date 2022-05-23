package ru.nikitin.voting.web.dish;

import lombok.extern.slf4j.Slf4j;
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

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.nikitin.voting.to.DishTo.getDishTo;
import static ru.nikitin.voting.web.dish.DishTestData.*;
import static ru.nikitin.voting.web.restaurant.RestaurantTestData.RESTAURANT_ID;
import static ru.nikitin.voting.web.user.UserTestData.ADMIN_MAIL;
import static ru.nikitin.voting.web.vote.VoteTestData.date;

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
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_TO_MATCHER.contentJson(getDishTo(dish1)));

    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    public void getAllMenu() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL, RESTAURANT_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_TO_MATCHER.contentJson(getDishesRestaurant(dish -> RESTAURANT_ID == dish.getRestaurant().id())));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    public void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + DISH_ID, RESTAURANT_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    public void update() throws Exception {
        DishTo updated = getDishTo(DishTestData.getUpdatedDish());
        perform(MockMvcRequestBuilders.put(REST_URL + DishTestData.DISH_ID_29, RESTAURANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());
        DishTestData.DISH_TO_MATCHER.assertMatch(service.get(RESTAURANT_ID, DishTestData.DISH_ID_29), getDishTo(DishTestData.getUpdatedDish()));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    public void create() throws Exception {
        DishTo newDish = getDishTo(DishTestData.getNewDish());
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL, RESTAURANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish)))
                .andExpect(status().isCreated());
        DishTo created = DishTestData.DISH_TO_MATCHER.readFromJson(action);
        Integer newId = created.getId();
        newDish.setId(newId);
        DishTestData.DISH_TO_MATCHER.assertMatch(created, newDish);
        DishTestData.DISH_TO_MATCHER.assertMatch(service.get(RESTAURANT_ID, newId), newDish);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    public void getMenuByRestaurantIdAndDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "byDate?date=" + date.toString(), RESTAURANT_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_TO_MATCHER.contentJson(getDishesRestaurant(dish -> RESTAURANT_ID == dish.getRestaurant().id() && dish.getServingDate().isEqual(date))));
    }
}