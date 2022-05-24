package ru.nikitin.voting.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.nikitin.voting.service.VoteService;
import ru.nikitin.voting.to.vote.VoteTo;
import ru.nikitin.voting.web.AbstractControllerTest;

import java.time.LocalTime;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.nikitin.voting.web.restaurant.RestaurantTestData.RESTAURANT_ID;
import static ru.nikitin.voting.web.user.UserTestData.ADMIN_MAIL;
import static ru.nikitin.voting.web.user.UserTestData.USER_MAIL;
import static ru.nikitin.voting.web.vote.VoteTestData.*;

class ProfileVoteControllerTest extends AbstractControllerTest {

    private static final String REST_URL = ProfileVoteController.VOTE_URL;

    @Autowired
    private VoteService service;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    public void firstVoting() throws Exception {
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL + "?restaurantId=" + RESTAURANT_ID))
                .andDo(print())
                .andExpect(status().isCreated());

        VoteTo created = VOTE_TO_MATCHER.readFromJson(action);
        int newId = created.getId();
        VOTE_TO_MATCHER.assertMatch(created, getNewTo(newId));
        VOTE_TO_MATCHER.assertMatch(new VoteTo(service.findById(newId)), getNewTo(newId));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void changeVote() throws Exception {
        if (LocalTime.now().compareTo(LocalTime.of(11, 0)) <= 0) {
            ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL + "?restaurantId=" + (RESTAURANT_ID + 1)))
                    .andDo(print())
                    .andExpect(status().isCreated());
            VoteTo updated = VOTE_TO_MATCHER.readFromJson(action);
            VOTE_TO_MATCHER.assertMatch(updated, getUpdatedTo());
        } else {
            perform(MockMvcRequestBuilders.post(REST_URL + "?restaurantId=" + RESTAURANT_ID))
                    .andDo(print())
                    .andExpect(status().isUnprocessableEntity())
                    .andExpect(content().string(containsString("You can't change your vote after 11:00")));
        }
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(new VoteTo(today), new VoteTo(nextLastVoteUser), new VoteTo(voteUser)));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/onDate?date=" + nowD))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(new VoteTo(today)));
    }
}