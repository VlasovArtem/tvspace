package com.vlasovartem.tvspace.utils.jsp.functions;

import com.vlasovartem.tvspace.entity.*;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.vlasovartem.tvspace.utils.jsp.functions.SeriesFunctions.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * Created by artemvlasov on 13/12/15.
 */
public class SeriesFunctionsTest {

    @Test
    public void joiningTest () {
        List<String> strings = Arrays.asList("Hello", "World", "!");
        assertTrue(strings.stream().collect(Collectors.joining(", ")).equals(joining(strings)));
    }

    @Test
    public void joiningWithNullTest () {
        assertNull(joining(null));
    }

    @Test
    public void checkNextEpisodeWithEpisodeDateGreaterThanTodayTest () {
        assertTrue(checkNextEpisode(LocalDate.now().plusMonths(1)));
    }

    @Test
    public void checkNextEpisodeTest () {
        assertTrue(checkNextEpisode(LocalDate.now()));
    }

    @Test
    public void checkNextEpisodeWithNotMatchesDateTest () {
        assertFalse(checkNextEpisode(LocalDate.now().minusDays(1)));
    }

    @Test
    public void checkUserSeriesTest () {
        String testId = "123";
        List<UserSeries> userSeries = Arrays.asList(new UserSeries("123", 1, 6), new UserSeries("321", 4, 5));
        assertTrue(checkUserSeries(testId, userSeries));
    }

    @Test
    public void checkUserSeriesWithNotMatchesDataTest () {
        String testId = "563";
        List<UserSeries> userSeries = Arrays.asList(new UserSeries("123", 1, 6), new UserSeries("321", 4, 5));
        assertFalse(checkUserSeries(testId, userSeries));
    }

    @Test
    public void findUserSeriesTest () {
        String testId = "123";
        List<UserSeries> userSeries = Arrays.asList(new UserSeries("123", 1, 6), new UserSeries("321", 4, 5));
        assertNotNull(findUserSeries(testId, userSeries));
    }

    @Test
    public void findUserSeriesWithNotMatchesDataTest () {
        String testId = "563";
        List<UserSeries> userSeries = Arrays.asList(new UserSeries("123", 1, 6), new UserSeries("321", 4, 5));
        assertNull(findUserSeries(testId, userSeries));
    }

    @Test
    public void findSeasonToSelectWithMatchesUserSeriesTest () {
        Series series = new Series();
        series.setId("123");
        int userSeriesSeason = 3;
        int userSeriesEpisode = 1;
        List<UserSeries> userSeries = Collections.singletonList(new UserSeries("123", userSeriesSeason, userSeriesEpisode));
        assertThat(findSeasonToSelect(series, userSeries), is(userSeriesSeason));
    }

    @Test
    public void findSeasonToSelectWithNextEpisodeTest () {
        Series series = new Series();
        Episode nextEpisode = new Episode();
        int nextSeasonNumber = 6;
        nextEpisode.setEpisodeDate(LocalDate.now().plusWeeks(1));
        nextEpisode.setSeasonNumber(nextSeasonNumber);
        series.setNextEpisode(nextEpisode);
        assertThat(findSeasonToSelect(series, null), is(nextSeasonNumber));
    }

    @Test
    public void findSeasonToSelectWithoutUserSeriesAndNextEpisode() {
        Series series = new Series();
        series.setSeasons(Collections.singletonList(new Season()));
        assertThat(findSeasonToSelect(series, null), is(1));
    }
}
