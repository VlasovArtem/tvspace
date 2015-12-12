package com.vlasovartem.tvspace.service;

import com.vlasovartem.tvspace.controller.model.Search;
import com.vlasovartem.tvspace.entity.Series;
import com.vlasovartem.tvspace.entity.UserSeries;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Set;

/**
 * Created by artemvlasov on 05/12/15.
 */
public interface SeriesService {

    String INIT_SORT_NAME = "Imdb Rating";
    String INIT_SORT_DIRECTION = "DESC";
    String NEXT_EPISODE_DATE_PROPERTY = "nextEpisode.episodeDate";

    List<Series> findSeries (String genre, Integer year, String title,
                             boolean hideFinished, boolean showUserSeries,
                             String sort, String direction);

    Set<String> findSeriesGenres ();

    Set<Integer> findSeriesYears ();

    List<Series> findAll(Sort sort);
}
