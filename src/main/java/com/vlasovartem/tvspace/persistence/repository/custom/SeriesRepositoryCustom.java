package com.vlasovartem.tvspace.persistence.repository.custom;

import com.vlasovartem.tvspace.entity.Series;

import java.util.List;
import java.util.Set;

/**
 * Created by artemvlasov on 25/11/15.
 */
public interface SeriesRepositoryCustom {
    Set<String> getSeriesGenres ();
    Set<Integer> getSeriesYears ();
    List<Series> findSeries(String genre, Integer year, String title, boolean hideFinished, boolean showUserSeries, String sort, String direction);
}
