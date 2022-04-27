package ru.javaops.topjava2.web.dish;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import ru.javaops.topjava2.model.Dish;
import ru.javaops.topjava2.repository.DishRepository;
import ru.javaops.topjava2.web.AbstractEntityAdminControllerTest;

import static ru.javaops.topjava2.web.dish.DishTestData.*;
import static ru.javaops.topjava2.web.user.UserTestData.ADMIN_MAIL;

class DishAdminRestControllerTest extends AbstractEntityAdminControllerTest<Dish> {

    private static final String REST_URL = DishAdminRestController.DISH_URL + '/';

    @Autowired
    private DishRepository repository;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void get() throws Exception {
        super.get(REST_URL, DISH_ID, DISH_MATCHER, dish1);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAll() throws Exception {
        super.getAll(REST_URL, DISH_MATCHER, dishes);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        super.delete(REST_URL, DISH_ID, repository);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void create() throws Exception {
        super.create(getNewDish(), REST_URL, DISH_MATCHER, repository);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        super.update(getUpdatedDish(), REST_URL, DISH_ID);
    }
}