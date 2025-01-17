package ru.nikitin.voting.web.user;

import ru.nikitin.voting.model.Role;
import ru.nikitin.voting.model.User;
import ru.nikitin.voting.util.JsonUtil;
import ru.nikitin.voting.web.MatcherFactory;

import java.util.Collections;
import java.util.Date;

public class UserTestData {
    public static final MatcherFactory.Matcher<User> USER_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(User.class, "registered", "password");

    public static final int USER_ID = 1;
    public static final int ADMIN_ID = 2;
    public static final int NOT_FOUND = 100;
    public static final String USER_MAIL = "user@yandex.ru";
    public static final String USER1_MAIL = "user1@yandex.ru";
    public static final String USER2_MAIL = "user2@yandex.ru";
    public static final String USER3_MAIL = "user3@yandex.ru";
    public static final String USER4_MAIL = "user4@yandex.ru";
    public static final String USER5_MAIL = "user5@yandex.ru";
    public static final String ADMIN_MAIL = "admin@gmail.com";

    public static final User user = new User(USER_ID, "User", USER_MAIL, "password", Role.USER);
    public static final User user1 = new User(USER_ID + 2, "User1", USER1_MAIL, "password1", Role.USER);
    public static final User user2 = new User(USER_ID + 3, "User2", USER2_MAIL, "password2", Role.USER);
    public static final User user3 = new User(USER_ID + 4, "User3", USER3_MAIL, "password3", Role.USER);
    public static final User user4 = new User(USER_ID + 5, "User4", USER4_MAIL, "password4", Role.USER);
    public static final User user5 = new User(USER_ID + 6, "User5", USER5_MAIL, "password5", Role.USER);

    public static final User admin = new User(ADMIN_ID, "Admin", ADMIN_MAIL, "admin", Role.ADMIN, Role.USER);

    public static User getNew() {
        return new User(null, "New", "new@gmail.com", "newPass", false, new Date(), Collections.singleton(Role.USER));
    }

    public static User getUpdated() {
        return new User(USER_ID, "UpdatedName", USER_MAIL, "newPass", false, new Date(), Collections.singleton(Role.ADMIN));
    }

    public static String jsonWithPassword(User user, String passw) {
        return JsonUtil.writeAdditionProps(user, "password", passw);
    }
}
