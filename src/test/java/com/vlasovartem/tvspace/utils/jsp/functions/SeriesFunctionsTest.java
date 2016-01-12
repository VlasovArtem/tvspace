package com.vlasovartem.tvspace.utils.jsp.functions;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vlasovartem.tvspace.entity.Episode;
import com.vlasovartem.tvspace.entity.Season;
import com.vlasovartem.tvspace.entity.Series;
import com.vlasovartem.tvspace.entity.UserSeries;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.vlasovartem.tvspace.utils.jsp.functions.SeriesFunctions.*;
import static org.hamcrest.Matchers.is;
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
    public void findSeasonToSelectWithoutUserSeriesAndNextEpisodeTest () {
        Series series = new Series();
        series.setSeasons(Collections.singletonList(new Season()));
        assertThat(findSeasonToSelect(series, null), is(1));
    }

    @Test
    public void findSeasonEpisodeLengthTest () {
        int searchSeason = 4;
        int amountOfEpisodes = 4;
        Series series = new Series();
        series.setSeasons(createSeasons(4));
        assertThat(findSeasonEpisodeLength(series, searchSeason), is(amountOfEpisodes));
    }

    @Test
    public void seriesSeasonMapTest () {
        ObjectNode preparedNode = JsonNodeFactory.instance.objectNode();
        preparedNode.put("1", 1).put("2", 2).put("3", 3).put("4", 4);
        Series series = new Series();
        series.setSeasons(createSeasons(4));
        assertEquals(seriesSeasonMap(series), preparedNode);
    }

    @Test
    public void seriesSeasonMapWithSeriesNullTest () {
        assertNull(seriesSeasonMap(null));
    }

    @Test
    public void findSeasonEpisodeToSelectWithUserSeriesTest () {
        Series series = new Series();
        int userWatchedSeason = 3;
        int userWatchedEpisode = 1;
        series.setId("123");
        List<UserSeries> userSeries = Collections.singletonList(new UserSeries("123", userWatchedSeason, userWatchedEpisode));
        assertThat(findSeasonEpisodeToSelect(series, userWatchedSeason, userSeries), is(userWatchedEpisode));
    }

    @Test
    public void findSeasonEpisodeToSelectWithNextEpisodeTest () {
        Series series = new Series();
        Episode nextEpisode = new Episode();
        int nextEpisodeNumber = 3;
        nextEpisode.setEpisodeNumber(nextEpisodeNumber);
        series.setNextEpisode(nextEpisode);
        assertThat(findSeasonEpisodeToSelect(series, 3, null), is(nextEpisodeNumber));
    }

    private List<Season> createSeasons (int numberOfSeasons) {
        if(numberOfSeasons > 0) {
            List<Season> seasons = new ArrayList<>(numberOfSeasons);
            for (int i = 0; i < numberOfSeasons; i++) {
                Season season = new Season();
                season.setSeasonNumber(i + 1);
                season.setEpisodes(createEpisodes(i + 1));
                seasons.add(season);
            }
            return seasons;
        }
        return null;
    }

    private List<Episode> createEpisodes(int numberOfEpisodes) {
        if(numberOfEpisodes > 0) {
            List<Episode> episodes = new ArrayList<>(numberOfEpisodes);
            for (int i = 0; i < numberOfEpisodes; i++) {
                episodes.add(new Episode());
            }
            return episodes;
        }
        return null;
    }
}
