package com.vlasovartem.tvspace.service.impl;

import com.vlasovartem.tvspace.entity.Series;
import com.vlasovartem.tvspace.persistence.repository.SeriesRepository;
import com.vlasovartem.tvspace.service.SeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Created by artemvlasov on 05/12/15.
 */
@Service
public class SeriesServiceImpl implements SeriesService {

    @Qualifier("seriesRepository")
    @Autowired
    private SeriesRepository seriesRepository;

    /**
     * Find series by passed parameters
     * @param genre Genre of the series
     * @param year Year of the series
     * @param title Title of the series
     * @param hideFinished hide finished
     * @return List<Series> when it`s match specific parameters
     */
    @Override
    public List<Series> findSeries(String genre, Integer year, String title, boolean hideFinished, Sort sort) {
        LocalDate yearStart = null;
        LocalDate yearEnd = null;
        if(Objects.nonNull(year)) {
            yearStart = LocalDate.of(year, Month.JANUARY, 1);
            yearEnd = LocalDate.of(year, Month.DECEMBER, 31);
        }
        switch (findSearchProperties(genre, year, title, hideFinished)) {
            case ALL_EXF:
                return seriesRepository.findByFinishedIsFalse(sort);
            case GENRE_TITLE_YEAR_EXF:
                return seriesRepository.findByGenresAndSeriesStartBetweenAndTitleLikeIgnoreCaseAndFinishedIsFalse
                        (genre, yearStart, yearEnd, title, sort);
            case GENRE_TITLE_YEAR:
                return seriesRepository.findByGenresAndSeriesStartBetweenAndTitleLikeIgnoreCase(genre, yearStart, yearEnd, title, sort);
            case GENRE_YEAR_EXF:
                return seriesRepository.findByGenresAndSeriesStartBetweenAndFinishedIsFalse(genre, yearStart, yearEnd, sort);
            case GENRE_YEAR:
                return seriesRepository.findByGenresAndSeriesStartBetween(genre, yearStart, yearEnd, sort);
            case GENRE_TITLE_EXF:
                return seriesRepository.findByGenresAndTitleLikeIgnoreCaseAndFinishedIsFalse (genre, title, sort);
            case GENRE_TITLE:
                return seriesRepository.findByGenresAndTitleLikeIgnoreCase (genre, title, sort);
            case YEAR_TITLE_EXF:
                return seriesRepository.findBySeriesStartBetweenAndTitleLikeIgnoreCaseAndFinishedIsFalse(yearStart, yearEnd, title, sort);
            case YEAR_TITLE:
                return seriesRepository.findBySeriesStartBetweenAndTitleLikeIgnoreCase(yearStart, yearEnd, title, sort);
            case TITLE_EXF:
                return seriesRepository.findByTitleLikeIgnoreCaseAndFinishedIsFalse(title, sort);
            case TITLE:
                return seriesRepository.findByTitleLikeIgnoreCase(title, sort);
            case GENRE_OR_YEAR_EXF:
                return seriesRepository.findByGenresOrSeriesStartBetweenAndFinishedIsFalse(genre, yearStart, yearEnd, sort);
            default:
                return seriesRepository.findByGenresOrSeriesStartBetween(genre, yearStart, yearEnd, sort);
        }
    }

    @Override
    public Set<String> findSeriesGenres() {
        return seriesRepository.getSeriesGenres();
    }

    @Override
    public Set<Integer> findSeriesYears() {
        return seriesRepository.getSeriesYears();
    }

    @Override
    public List<Series> findAll(Sort sort) {
        return seriesRepository.findAll(sort);
    }

    @Override
    public List<Series> findFinished(Sort sort) {
        return seriesRepository.findByFinishedIsFalse(sort);
    }

    @Override
    public List<Series> findNextEpisodes(Sort sort) {
        return seriesRepository.findNextEpisode (LocalDate.now(), sort);
    }

    @Override
    public List<Series> findUserSeries(Sort sort) {
        return null;
    }

    private SearchProperty findSearchProperties(String genre, Integer year, String title, boolean hideFinished) {
        if (Objects.isNull(genre) && Objects.isNull(year) && Objects.isNull(title) && hideFinished) {
            return SearchProperty.ALL_EXF;
        } else if (Objects.nonNull(genre) && Objects.nonNull(year) && year != 0 && Objects.nonNull(title)) {
            if (hideFinished) return SearchProperty.GENRE_TITLE_YEAR_EXF;
            return SearchProperty.GENRE_TITLE_YEAR;
        } else if (Objects.nonNull(genre) && Objects.nonNull(year)) {
            if(hideFinished) return SearchProperty.GENRE_YEAR_EXF;
            return SearchProperty.GENRE_YEAR;
        } else if (Objects.nonNull(genre) && Objects.nonNull(title)) {
            if(hideFinished) return SearchProperty.GENRE_TITLE_EXF;
            return SearchProperty.GENRE_TITLE;
        } else if (Objects.nonNull(year) && Objects.nonNull(title)) {
            if(hideFinished) return SearchProperty.YEAR_TITLE_EXF;
            return SearchProperty.YEAR_TITLE;
        } else if (Objects.isNull(year) && Objects.isNull(genre)) {
            if(hideFinished) return SearchProperty.TITLE_EXF;
            return SearchProperty.TITLE;
        } else {
            if(hideFinished) return SearchProperty.GENRE_OR_YEAR_EXF;
            return SearchProperty.GENRE_OR_YEAR;
        }
    }

    /**
     * Enum for search format. EXF - exclude finished. All properties is divided by and GENRE_YEAR - means
     * GENRE_AND_YEAR
     */
    private enum SearchProperty {
        ALL, ALL_EXF, GENRE_YEAR, GENRE_YEAR_EXF, YEAR_TITLE, YEAR_TITLE_EXF, GENRE_TITLE,
        GENRE_TITLE_EXF, TITLE, TITLE_EXF, GENRE_TITLE_YEAR, GENRE_TITLE_YEAR_EXF, GENRE_OR_YEAR, GENRE_OR_YEAR_EXF
    }
}
