package ru.nikitin.voting.web;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.nikitin.voting.util.JsonUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.nikitin.voting.web.user.UserTestData.USER_MAIL;
import static ru.nikitin.voting.web.vote.VoteTestData.date;
import static ru.nikitin.voting.web.vote.VoteTestData.getVotingTo;

@Slf4j
class MenuUIControllerTest extends AbstractControllerTest {

    private static final String REST_URL = MenuUIController.MENU_URL + '/';


    @Test
    @WithUserDetails(value = USER_MAIL)
    void getVoting() throws Exception {
        ResultActions action = perform(MockMvcRequestBuilders.get(REST_URL + date.toString()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        String received = action.andReturn().getResponse().getContentAsString();
        assertEquals(received, JsonUtil.writeValue(getVotingTo(date)));
    }
}