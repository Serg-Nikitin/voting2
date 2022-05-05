package ru.javaops.topjava2.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.topjava2.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static ru.javaops.topjava2.web.restaurant.RestaurantTestData.RESTAURANT_ID;
import static ru.javaops.topjava2.web.user.UserTestData.USER_MAIL;
import static ru.javaops.topjava2.web.vote.VoteTestData.VOTE_MATCHER;
import static ru.javaops.topjava2.web.vote.VoteTestData.voteUserNow;

class ProfileVoteControllerTest extends AbstractControllerTest {

    private static final String REST_URL = ProfileVoteController.VOTE_URL + '/';


    @Test
    @WithUserDetails(value = USER_MAIL)
    public void voting() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT_ID))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(voteUserNow));
    }

   /* @Test
    @WithUserDetails(value = USER_MAIL)
    public void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(listUserVote));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    public void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + VOTE_ID))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(voteUser));
    }*/

}