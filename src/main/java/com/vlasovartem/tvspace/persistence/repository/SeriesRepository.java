package com.vlasovartem.tvspace.persistence.repository;

import com.vlasovartem.tvspace.entity.Series;
import com.vlasovartem.tvspace.persistence.repository.custom.SeriesRepositoryCustom;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by artemvlasov on 20/11/15.
 */
@Repository
public interface SeriesRepository extends MongoRepository<Series, String>, SeriesRepositoryCustom {

    List<Series> findByGenresOrYearStartAndFinishedIsFalse (String genre, int year, Sort sort);

    List<Series> findByGenresOrYearStart (String genre, int year, Sort sort);

    List<Series> findByGenresAndYearStartAndFinishedIsFalse (String genre, Integer year, Sort sort);

    List<Series> findByGenresAndYearStart (String genre, Integer year, Sort sort);

    List<Series> findByGenresAndYearStartAndTitleLikeIgnoreCaseAndFinishedIsFalse (String genre, Integer year, String title, Sort sort);

    List<Series> findByGenresAndYearStartAndTitleLikeIgnoreCase (String genre, Integer year, String title, Sort sort);

    List<Series> findByGenresAndTitleLikeIgnoreCaseAndFinishedIsFalse (String genre, String title, Sort sort);

    List<Series> findByGenresAndTitleLikeIgnoreCase (String genre, String title, Sort sort);

    List<Series> findByYearStartAndTitleLikeIgnoreCaseAndFinishedIsFalse (Integer year, String title, Sort sort);

    List<Series> findByYearStartAndTitleLikeIgnoreCase (Integer year, String title, Sort sort);

    List<Series> findByTitleLikeIgnoreCaseAndFinishedIsFalse(String title, Sort sort);

    List<Series> findByTitleLikeIgnoreCase (String title, Sort sort);

    List<Series> findByFinishedIsFalse (Sort sort);
}
