package com.vlasovartem.tvspace.persistence.repository;

import com.vlasovartem.tvspace.entity.Series;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by artemvlasov on 20/11/15.
 */
public interface SeriesRepository extends MongoRepository<Series, String> {
}
