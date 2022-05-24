package ru.nikitin.voting.web.vote;

import ru.nikitin.voting.model.Vote;
import ru.nikitin.voting.to.vote.VoteTo;
import ru.nikitin.voting.to.vote.VotingTo;
import ru.nikitin.voting.web.MatcherFactory;
import ru.nikitin.voting.web.restaurant.RestaurantTestData;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static ru.nikitin.voting.web.user.UserTestData.*;

public class VoteTestData {

    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Vote.class, "user.registered", "user.password");
    public static final MatcherFactory.Matcher<VotingTo> VOTING_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(VotingTo.class);
    public static final MatcherFactory.Matcher<VoteTo> VOTE_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(VoteTo.class);

    public static LocalDate date = LocalDate.of(2022, Month.APRIL, 20);
    public static LocalDate nowD = LocalDate.now();

    public static List<VotingTo> getVotingTo(LocalDate date) {
        return listAll.stream()
                .filter(v -> v.getVoteDate().isEqual(date)).collect(Collectors.groupingBy(Vote::getRestaurant, Collectors.counting()))
                .entrySet().stream().map(o -> new VotingTo(date, o.getKey(), o.getValue())).collect(Collectors.toList());
    }

    public static VoteTo getNewTo(int id) {
        return new VoteTo(id, nowD, RestaurantTestData.family);
    }

    public static VoteTo getUpdatedTo() {
        return new VoteTo(15, nowD, RestaurantTestData.georgia);
    }


    public static final int VOTE_ID = 1;

    public static final Vote voteUser = new Vote(VOTE_ID, LocalDate.of(2022, Month.APRIL, 20), RestaurantTestData.family, user);
    public static final Vote nextLastVoteUser = new Vote(VOTE_ID + 7, LocalDate.of(2022, Month.APRIL, 21), RestaurantTestData.family, user);

    public static final Vote voteAdmin = new Vote(VOTE_ID + 1, LocalDate.of(2022, Month.APRIL, 20), RestaurantTestData.clouds, admin);
    public static final Vote nextVoteAdmin = new Vote(VOTE_ID + 8, LocalDate.of(2022, Month.APRIL, 21), RestaurantTestData.clouds, admin);

    public static final Vote votU1 = new Vote(VOTE_ID + 2, LocalDate.of(2022, Month.APRIL, 20), RestaurantTestData.odessa, user1);
    public static final Vote nextVotU1 = new Vote(VOTE_ID + 9, LocalDate.of(2022, Month.APRIL, 21), RestaurantTestData.odessa, user1);
    public static final Vote votU2 = new Vote(VOTE_ID + 3, LocalDate.of(2022, Month.APRIL, 20), RestaurantTestData.family, user2);
    public static final Vote nextVotU2 = new Vote(VOTE_ID + 10, LocalDate.of(2022, Month.APRIL, 21), RestaurantTestData.family, user2);
    public static final Vote votU3 = new Vote(VOTE_ID + 4, LocalDate.of(2022, Month.APRIL, 20), RestaurantTestData.family, user3);
    public static final Vote nextVotU3 = new Vote(VOTE_ID + 11, LocalDate.of(2022, Month.APRIL, 21), RestaurantTestData.family, user3);
    public static final Vote votU4 = new Vote(VOTE_ID + 5, LocalDate.of(2022, Month.APRIL, 20), RestaurantTestData.family, user4);
    public static final Vote nextVotU4 = new Vote(VOTE_ID + 12, LocalDate.of(2022, Month.APRIL, 21), RestaurantTestData.family, user4);
    public static final Vote votU5 = new Vote(VOTE_ID + 6, LocalDate.of(2022, Month.APRIL, 20), RestaurantTestData.georgia, user5);
    public static final Vote nextVotU5 = new Vote(VOTE_ID + 13, LocalDate.of(2022, Month.APRIL, 21), RestaurantTestData.georgia, user5);

    public static final Vote today = new Vote(VOTE_ID + 14, LocalDate.now(), RestaurantTestData.family, user);

    public static List<Vote> listAll = Arrays.asList(voteUser, nextLastVoteUser, voteAdmin, nextVoteAdmin, votU1, nextVotU1, votU2, nextVotU2, votU3, nextVotU3, votU4, nextVotU4, votU5, nextVotU5, today);


}
