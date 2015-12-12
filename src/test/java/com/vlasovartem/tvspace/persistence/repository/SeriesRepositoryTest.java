package com.vlasovartem.tvspace.persistence.repository;

import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;
import com.vlasovartem.tvspace.WithMockUser;
import com.vlasovartem.tvspace.config.AppInitializer;
import com.vlasovartem.tvspace.config.ServletContextConfig;
import com.vlasovartem.tvspace.config.TestAppConfig;
import com.vlasovartem.tvspace.config.security.SecurityConfig;
import com.vlasovartem.tvspace.service.SeriesService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;

/**
 * Created by artemvlasov on 10/12/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestAppConfig.class})
@ActiveProfiles("test")
@UsingDataSet(locations = "series-data-test.json", loadStrategy = LoadStrategyEnum.CLEAN_INSERT)
public class SeriesRepositoryTest {

    @Autowired
    private SeriesRepository seriesRepository;

    @Test
    public void findSeriesGenresTest () {
        Set<String> genres = seriesRepository.getSeriesGenres();
        assertNotNull(genres);
        assertTrue(genres.size() > 0);
    }

    @Test
    public void findSeriesYearsTest () {
        Set<Integer> year = seriesRepository.getSeriesYears();
        assertNotNull(year);
        assertTrue(year.size() > 0);
    }

    @Test
    public void findSeriesWithoutUserSeriesIdsTest () {
        assertThat(seriesRepository.findSeries("Adventure" , 2011, "game", true, false, "title", "DESC"),
                hasSize(1));
    }

    @Test
    public void findSeriesWithGenreTest () {
        assertThat(seriesRepository.findSeries("Adventure", null, null, false, false, null, null), hasSize(2));
    }

    @Test
    public void findSeriesWithInvalidGenreTest () {
        assertThat(seriesRepository.findSeries("hello", null, null, false, false, null, null), hasSize(0));
    }

    @Test
    public void findSeriesWithYearTest () {
        assertThat(seriesRepository.findSeries(null, 2015, null, false, false, null, null), hasSize(1));
    }

    @Test
    public void findSeriesWithInvalidYearTest () {
        assertThat(seriesRepository.findSeries(null, 1639, null, false, false, null, null), hasSize(0));
    }

    @Test
    public void findSeriesWithTitleTest () {
        assertThat(seriesRepository.findSeries(null, null, "arrow", false, false, null, null), hasSize(1));
    }

    @Test
    public void findSeriesWithInvalidTitleTest () {
        assertThat(seriesRepository.findSeries(null, null, "hello", false, false, null, null), hasSize(0));
    }

    @Test
    public void findSeriesWithHideFinishedTest () {
        assertThat(seriesRepository.findSeries(null, null, null, true, false, null, null), hasSize(3));
    }

    @Test
    public void findSeriesWithoutHideFinishedTest () {
        assertThat(seriesRepository.findSeries(null, null, null, false, false, null, null), hasSize(4));
    }

    @Test
    public void findSeriesWithGenreAndYearTest () {
        assertThat(seriesRepository.findSeries("Action", 2012, null, false, false, null, null), hasSize(1));
    }

    @Test
    public void findSeriesWithGenreAndYearWithInvalidDataTest () {
        assertThat(seriesRepository.findSeries("Action", 2010, null, false, false, null, null), hasSize(0));
    }

    @Test
    public void findSeriesWithGenreAndTitleTest () {
        assertThat(seriesRepository.findSeries("Action", null, "arrow", false, false, null, null), hasSize(1));
    }

    @Test
    public void findSeriesWithGenreAndTitleWithInvalidDataTest () {
        assertThat(seriesRepository.findSeries("hello", null, "arrow", false, false, null, null), hasSize(0));
    }

    @Test
    public void findSeriesWithGenreAndYearAndTitleTest () {
        assertThat(seriesRepository.findSeries("Adventure" , 2011, "game", false, false, null, null), hasSize(1));
    }

    @Test
    public void findSeriesWithGenreAndYearAndTitleWithInvalidDataTest () {
        assertThat(seriesRepository.findSeries("hello" , 2011, "game", false, false, null, null), hasSize(0));
    }

    @Test
    public void findSeriesWithNextEpisodeTest () {
        assertThat(seriesRepository.findSeries("Drama" , 2015, "Limitless", false, false, SeriesService
                .NEXT_EPISODE_DATE_PROPERTY, Sort.Direction.DESC.name()), hasSize(1));
    }

    @Test
    public void findSeriesWithoutNextEpisodeTest () {
        assertThat(seriesRepository.findSeries("Drama" , 2014, "Forever", false, false, SeriesService
                .NEXT_EPISODE_DATE_PROPERTY, Sort.Direction.DESC.name()), hasSize(0));
    }

    @Test
    @WithMockUser(username = "vlasovartem")
    public void findSeriesTest () {
        assertThat(seriesRepository.findSeries("Adventure" , 2011, "game", true, true, "title", "DESC"),
                hasSize(1));
    }
}
