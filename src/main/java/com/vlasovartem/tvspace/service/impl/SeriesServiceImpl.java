package com.vlasovartem.tvspace.service.impl;

import com.vlasovartem.tvspace.entity.Series;
import com.vlasovartem.tvspace.persistence.repository.SeriesRepository;
import com.vlasovartem.tvspace.service.SeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * Created by artemvlasov on 05/12/15.
 */
@Service
public class SeriesServiceImpl implements SeriesService {

    @Qualifier("seriesRepository")
    @Autowired
    private SeriesRepository seriesRepository;

    @Override
    public List<Series> findSeries
            (String genre, Integer year, String title, boolean hideFinished, boolean showUserSeries, String sort, String direction) {
        return seriesRepository.findSeries(genre, year, title, hideFinished, showUserSeries, sort,
                direction);
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
}
