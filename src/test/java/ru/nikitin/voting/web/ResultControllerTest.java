package ru.nikitin.voting.web;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.nikitin.voting.web.dish.DishTestData.DISH_TO_MATCHER;
import static ru.nikitin.voting.web.dish.DishTestData.getDishToByDate;
import static ru.nikitin.voting.web.user.UserTestData.USER_MAIL;
import static ru.nikitin.voting.web.vote.VoteTestData.*;

@Slf4j
class ResultControllerTest extends AbstractControllerTest {

    private static final String REST_URL = ResultController.MENU_URL;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getVoting() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "voting/byDate?date=" + date.toString()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTING_TO_MATCHER.contentJson(getVotingTo(date)));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getDishes() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "dishes/byDate?date=" + date.toString()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_TO_MATCHER.contentJson(getDishToByDate(date)));
    }
}