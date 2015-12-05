package com.vlasovartem.tvspace.persistence.repository.custom.impl;

import com.vlasovartem.tvspace.entity.SearchInfo;
import com.vlasovartem.tvspace.entity.Series;
import com.vlasovartem.tvspace.persistence.repository.custom.SeriesRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

/**
 * Created by artemvlasov on 25/11/15.
 */
@Repository
public class SeriesRepositoryImpl implements SeriesRepositoryCustom {

    private MongoOperations operations;

    @Autowired
    public SeriesRepositoryImpl(MongoOperations operations) {
        this.operations = operations;
    }

    @Override
    public Set<String> getSeriesGenres() {
        Aggregation aggregation = newAggregation(
                unwind("genres"),
                group().addToSet("genres").as("genres"),
                project("genres").andExclude("_id")
        );
        return operations
                .aggregate(aggregation, Series.class, SearchInfo.class)
                .getUniqueMappedResult().getGenres().stream().sorted().collect(Collectors.toSet());
    }

    @Override
    public Set<Integer> getSeriesYears() {
        Aggregation aggregation = newAggregation(
                group().addToSet("seriesStart").as("years"),
                project("years").andExclude("_id")
        );
        return operations.aggregate(aggregation, Series.class, SearchInfo.class).getUniqueMappedResult().getYears()
                .stream().map(LocalDate::getYear).collect(Collectors.toSet());
    }

}
