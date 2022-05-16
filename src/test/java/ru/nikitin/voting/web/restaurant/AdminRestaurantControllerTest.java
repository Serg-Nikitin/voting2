package ru.nikitin.voting.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.nikitin.voting.model.Restaurant;
import ru.nikitin.voting.repository.RestaurantRepository;
import ru.nikitin.voting.util.JsonUtil;
import ru.nikitin.voting.web.AbstractControllerTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.nikitin.voting.web.user.UserTestData.ADMIN_MAIL;

class AdminRestaurantControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminRestaurantController.RESTAURANT_URL + '/';

    @Autowired
    private RestaurantRepository repository;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RestaurantTestData.RESTAURANT_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RestaurantTestData.RESTAURANT_MATCHER.contentJson(RestaurantTestData.family));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RestaurantTestData.RESTAURANT_MATCHER.contentJson(RestaurantTestData.family, RestaurantTestData.georgia, RestaurantTestData.odessa, RestaurantTestData.clouds));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + RestaurantTestData.RESTAURANT_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertFalse(repository.findById(RestaurantTestData.RESTAURANT_ID).isPresent());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void create() throws Exception {
        Restaurant restaurant = RestaurantTestData.getNewRestaurant();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(restaurant)))
                .andExpect(status().isCreated());
        Restaurant created = RestaurantTestData.RESTAURANT_MATCHER.readFromJson(action);
        int newId = created.id();
        restaurant.setId(newId);
        RestaurantTestData.RESTAURANT_MATCHER.assertMatch(created, restaurant);
        RestaurantTestData.RESTAURANT_MATCHER.assertMatch(repository.getById(newId), restaurant);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        perform(MockMvcRequestBuilders.put(REST_URL + RestaurantTestData.RESTAURANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(RestaurantTestData.getUpdatedRestaurant())))
                .andDo(print())
                .andExpect(status().isNoContent());
        RestaurantTestData.RESTAURANT_MATCHER.assertMatch(repository.getById(RestaurantTestData.RESTAURANT_ID), RestaurantTestData.getUpdatedRestaurant());
    }
}