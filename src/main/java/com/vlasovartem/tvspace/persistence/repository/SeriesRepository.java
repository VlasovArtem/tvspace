package com.vlasovartem.tvspace.persistence.repository;

import com.vlasovartem.tvspace.entity.Series;
import com.vlasovartem.tvspace.persistence.repository.custom.SeriesRepositoryCustom;
import org.springframework.cglib.core.Local;
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
public interface SeriesRepository extends MongoRepository<Series, String>, SeriesRepositoryCustom {}
