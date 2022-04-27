package ru.javaops.topjava2.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.repository.RestaurantRepository;
import ru.javaops.topjava2.web.AbstractEntityAdminControllerTest;

import java.util.Arrays;

import static ru.javaops.topjava2.web.restaurant.RestaurantTestData.*;
import static ru.javaops.topjava2.web.user.UserTestData.ADMIN_MAIL;

class RestaurantAdminControllerTest extends AbstractEntityAdminControllerTest<Restaurant> {

    private static final String REST_URL = RestaurantAdminController.RESTAURANT_URL + '/';

    @Autowired
    private RestaurantRepository repository;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void get() throws Exception {
        super.get(REST_URL, RESTAURANT_ID, RESTAURANT_MATCHER, family);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAll() throws Exception {
        super.getAll(REST_URL, RESTAURANT_MATCHER, Arrays.asList(family, georgia, odessa, clouds));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        super.delete(REST_URL, RESTAURANT_ID, repository);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void create() throws Exception {
        super.create(getNewRestaurant(), REST_URL, RESTAURANT_MATCHER, repository);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        super.update(getUpdatedRestaurant(), REST_URL, RESTAURANT_ID);
    }
}