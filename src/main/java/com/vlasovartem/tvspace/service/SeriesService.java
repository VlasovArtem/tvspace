package com.vlasovartem.tvspace.service;

import com.vlasovartem.tvspace.entity.Series;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Set;

/**
 * Created by artemvlasov on 05/12/15.
 */
public interface SeriesService {
    List<Series> findSeries (String genre, Integer year, String title, boolean hideFinished, Sort sort);
    Set<String> findSeriesGenres ();
    Set<Integer> findSeriesYears ();
    List<Series> findAll(Sort sort);
    List<Series> findFinished(Sort sort);
}
