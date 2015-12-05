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
        if (Objects.isNull(genre) && Objects.isNull(year) && Objects.isNull(title) && hideFinished) {
            return seriesRepository.findByFinishedIsFalse(sort);
        } else if (Objects.nonNull(genre) && Objects.nonNull(year) && year != 0 && Objects.nonNull(title)) {
            if (hideFinished) return seriesRepository.findByGenresAndSeriesStartBetweenAndTitleLikeIgnoreCaseAndFinishedIsFalse
                    (genre, yearStart, yearEnd, title, sort);
            else return seriesRepository.findByGenresAndSeriesStartBetweenAndTitleLikeIgnoreCase(genre, yearStart, yearEnd, title, sort);
        } else if (Objects.nonNull(genre) && Objects.nonNull(year)) {
            if (hideFinished) return seriesRepository.findByGenresAndSeriesStartBetweenAndFinishedIsFalse(genre, yearStart, yearEnd, sort);
            else return seriesRepository.findByGenresAndSeriesStartBetween(genre, yearStart, yearEnd, sort);
        } else if (Objects.nonNull(genre) && Objects.nonNull(title)) {
            if (hideFinished) return seriesRepository.findByGenresAndTitleLikeIgnoreCaseAndFinishedIsFalse (genre, title, sort);
            else return seriesRepository.findByGenresAndTitleLikeIgnoreCase (genre, title, sort);
        } else if (Objects.nonNull(year) && Objects.nonNull(title)) {
            if (hideFinished) return seriesRepository.findBySeriesStartBetweenAndTitleLikeIgnoreCaseAndFinishedIsFalse(yearStart, yearEnd, title, sort);
            else return seriesRepository.findBySeriesStartBetweenAndTitleLikeIgnoreCase(yearStart, yearEnd, title, sort);
        } else if (Objects.isNull(year) && Objects.isNull(genre)) {
            if (hideFinished) return seriesRepository.findByTitleLikeIgnoreCaseAndFinishedIsFalse(title, sort);
            else return seriesRepository.findByTitleLikeIgnoreCase(title, sort);
        } else {
            if (hideFinished) return seriesRepository.findByGenresOrSeriesStartBetweenAndFinishedIsFalse(genre, yearStart, yearEnd, sort);
            else return seriesRepository.findByGenresOrSeriesStartBetween(genre, yearStart, yearEnd, sort);
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
}
