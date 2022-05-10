package ru.javaops.topjava2.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.topjava2.model.Vote;
import ru.javaops.topjava2.service.VoteService;
import ru.javaops.topjava2.util.JsonUtil;
import ru.javaops.topjava2.web.AbstractControllerTest;

import java.time.LocalTime;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.topjava2.web.user.UserTestData.*;
import static ru.javaops.topjava2.web.vote.VoteTestData.*;

class ProfileVoteControllerTest extends AbstractControllerTest {

    private static final String REST_URL = ProfileVoteController.VOTE_URL + '/';

    @Autowired
    private VoteService service;


    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    public void voting() throws Exception {
        Vote aNew = VoteTestData.getNew();

        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(aNew)))
                .andDo(print())
                .andExpect(status().isCreated());

        Vote created = VOTE_MATCHER.readFromJson(action);
        Integer newId = created.getId();
        aNew.setId(newId);
        aNew.setUser(admin);
        VOTE_MATCHER.assertMatch(created, aNew);
        VOTE_MATCHER.assertMatch(service.getById(newId), aNew);

    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void update() throws Exception {
        Vote updated = getUpdatedVote();
        if (LocalTime.now().compareTo(LocalTime.of(11, 0)) <= 0) {
            perform(MockMvcRequestBuilders.put(REST_URL + VOTE_UPDATED).contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtil.writeValue(updated)))
                    .andDo(print())
                    .andExpect(status().isNoContent());

            VOTE_MATCHER.assertMatch(service.getById(VOTE_UPDATED), updated);
        } else {
            perform(MockMvcRequestBuilders.put(REST_URL + VOTE_UPDATED).contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtil.writeValue(updated)))
                    .andDo(print())
                    .andExpect(status().isUnprocessableEntity())
                    .andExpect(content().string(containsString("You can't change your vote after 11:00")));
        }
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(voteUser,nextLastVoteUser,today));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + DATE))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(today));
    }
}