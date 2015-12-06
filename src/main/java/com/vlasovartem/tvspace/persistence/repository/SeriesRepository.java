package com.vlasovartem.tvspace.persistence.repository;

import com.vlasovartem.tvspace.entity.Series;
import com.vlasovartem.tvspace.persistence.repository.custom.SeriesRepositoryCustom;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by artemvlasov on 20/11/15.
 */
@Repository
public interface SeriesRepository extends MongoRepository<Series, String>, SeriesRepositoryCustom {

    List<Series> findByGenresOrSeriesStartBetweenAndFinishedIsFalse(String genre, LocalDate yearStart, LocalDate
            yearEnd, Sort sort);

    List<Series> findByGenresOrSeriesStartBetween(String genre, LocalDate yearStart, LocalDate
            yearEnd, Sort sort);

    List<Series> findByGenresAndSeriesStartBetweenAndFinishedIsFalse(String genre, LocalDate yearStart, LocalDate
            yearEnd, Sort sort);

    List<Series> findByGenresAndSeriesStartBetween(String genre, LocalDate yearStart, LocalDate
            yearEnd, Sort sort);

    List<Series> findByGenresAndSeriesStartBetweenAndTitleLikeIgnoreCaseAndFinishedIsFalse(String genre, LocalDate yearStart, LocalDate
            yearEnd, String title, Sort sort);

    List<Series> findByGenresAndSeriesStartBetweenAndTitleLikeIgnoreCase(String genre, LocalDate yearStart, LocalDate
            yearEnd, String title, Sort sort);

    List<Series> findByGenresAndTitleLikeIgnoreCaseAndFinishedIsFalse (String genre, String title, Sort sort);

    List<Series> findByGenresAndTitleLikeIgnoreCase (String genre, String title, Sort sort);

    List<Series> findBySeriesStartBetweenAndTitleLikeIgnoreCaseAndFinishedIsFalse(LocalDate yearStart, LocalDate
            yearEnd, String title, Sort sort);

    List<Series> findBySeriesStartBetweenAndTitleLikeIgnoreCase(LocalDate yearStart, LocalDate
            yearEnd, String title, Sort sort);

    List<Series> findByTitleLikeIgnoreCaseAndFinishedIsFalse(String title, Sort sort);

    List<Series> findByTitleLikeIgnoreCase (String title, Sort sort);

    List<Series> findByFinishedIsFalse (Sort sort);

    @Query(value = "{$and : [{'nextEpisode.episodeDate' : {$exists : true}}, {'nextEpisode.episodeDate' : {$gt : ?0}}]}")
    List<Series> findNextEpisode(LocalDate today, Sort sort);
}
