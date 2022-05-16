package ru.nikitin.voting.web.vote;

import ru.nikitin.voting.model.Dish;
import ru.nikitin.voting.model.Restaurant;
import ru.nikitin.voting.model.Vote;
import ru.nikitin.voting.to.DishTo;
import ru.nikitin.voting.to.VotingTo;
import ru.nikitin.voting.web.MatcherFactory;
import ru.nikitin.voting.web.dish.DishTestData;
import ru.nikitin.voting.web.restaurant.RestaurantTestData;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.nikitin.voting.web.user.UserTestData.*;

public class VoteTestData {

    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Vote.class, "user.registered", "user.password");

    public static LocalDate date = LocalDate.now();

    public static VotingTo getVotingTo(LocalDate date) {
        Map<Restaurant, Long> rating = listAll.stream()
                .filter(v -> v.getDateVote().compareTo(date) == 0)
                .collect(Collectors.groupingBy(Vote::getRestaurant, Collectors.counting()));
        Map<Restaurant, List<DishTo>> menu = DishTestData.dishes
                .stream()
                .filter(d -> d.getDateOfServing().compareTo(date) == 0)
                .collect(Collectors.groupingBy(Dish::getRestaurant, Collectors.mapping(DishTo::new, Collectors.toList())));

        menu.keySet().forEach(r -> rating.computeIfAbsent(r, k -> 0L));

        return new VotingTo(date, rating, menu);
    }

    public static final int VOTE_ID = 1;
    public static final int VOTE_UPDATED = 15;
    public static final String DATE = LocalDate.now().toString();

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


    public static Vote getNew() {
        return new Vote(LocalDate.now(), RestaurantTestData.family);
    }

    public static Vote getUpdatedVote() {
        return new Vote(15, LocalDate.now(), RestaurantTestData.georgia, user);
    }


    public static List<Vote> listAll = Arrays.asList(voteUser, nextLastVoteUser, voteAdmin, nextVoteAdmin, votU1, nextVotU1, votU2, nextVotU2, votU3, nextVotU3, votU4, nextVotU4, votU5, nextVotU5, today);


}
