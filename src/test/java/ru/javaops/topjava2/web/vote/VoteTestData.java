package ru.javaops.topjava2.web.vote;

import ru.javaops.topjava2.model.Vote;
import ru.javaops.topjava2.web.MatcherFactory;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javaops.topjava2.web.restaurant.RestaurantTestData.*;
import static ru.javaops.topjava2.web.user.UserTestData.*;

public class VoteTestData {

    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Vote.class, "user.registered", "user.password");
    public static final int VOTE_ID = 1;

    public static final Vote voteUserNow = new Vote(VOTE_ID + 16, LocalDate.now(), family, user, true);

    public static final Vote voteUser = new Vote(VOTE_ID, LocalDate.of(2022, Month.APRIL, 20), family, user, true);
    public static final Vote lastVoteUser = new Vote(VOTE_ID + 1, LocalDate.of(2022, Month.APRIL, 20), georgia, user, false);
    public static final Vote nextLastVoteUser = new Vote(VOTE_ID + 8, LocalDate.of(2022, Month.APRIL, 21), family, user, true);
    public static final Vote nextVoteUser = new Vote(VOTE_ID + 9, LocalDate.of(2022, Month.APRIL, 21), odessa, user, false);

    public static final Vote voteAdmin = new Vote(VOTE_ID + 2, LocalDate.of(2022, Month.APRIL, 20), clouds, admin, true);
    public static final Vote nextVoteAdmin = new Vote(VOTE_ID + 10, LocalDate.of(2022, Month.APRIL, 21), clouds, admin, true);

    public static final Vote votU1 = new Vote(VOTE_ID + 3, LocalDate.of(2022, Month.APRIL, 20), odessa, user1, true);
    public static final Vote nextVotU1 = new Vote(VOTE_ID + 11, LocalDate.of(2022, Month.APRIL, 21), odessa, user1, true);
    public static final Vote votU2 = new Vote(VOTE_ID + 4, LocalDate.of(2022, Month.APRIL, 20), family, user2, true);
    public static final Vote nextVotU2 = new Vote(VOTE_ID + 12, LocalDate.of(2022, Month.APRIL, 21), family, user2, true);
    public static final Vote votU3 = new Vote(VOTE_ID + 5, LocalDate.of(2022, Month.APRIL, 20), family, user3, true);
    public static final Vote nextVotU3 = new Vote(VOTE_ID + 13, LocalDate.of(2022, Month.APRIL, 21), family, user3, true);
    public static final Vote votU4 = new Vote(VOTE_ID + 6, LocalDate.of(2022, Month.APRIL, 20), family, user4, true);
    public static final Vote nextVotU4 = new Vote(VOTE_ID + 14, LocalDate.of(2022, Month.APRIL, 21), family, user4, true);
    public static final Vote votU5 = new Vote(VOTE_ID + 7, LocalDate.of(2022, Month.APRIL, 20), georgia, user5, true);
    public static final Vote nextVotU5 = new Vote(VOTE_ID + 15, LocalDate.of(2022, Month.APRIL, 21), georgia, user5, true);


    public static List<Vote> listAll = Arrays.asList(voteUser, lastVoteUser, nextLastVoteUser, nextVoteUser, voteAdmin, nextVoteAdmin, votU1, nextVotU1, votU2, nextVotU2, votU3, nextVotU3, votU4, nextVotU4, votU5, nextVotU5);
    public static List<Vote> listUserVote = listAll.stream().filter(vote -> vote.getUser().getId().equals(USER_ID)).sorted(Comparator.comparing(Vote::getDateVote).reversed()).collect(Collectors.toList());

}
