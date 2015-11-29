package com.vlasovartem.tvspace.persistence.repository.custom;

import java.util.Set;

/**
 * Created by artemvlasov on 25/11/15.
 */
public interface SeriesRepositoryCustom {
    Set<String> getSeriesGenres ();
    Set<Integer> getSeriesYears ();
}
