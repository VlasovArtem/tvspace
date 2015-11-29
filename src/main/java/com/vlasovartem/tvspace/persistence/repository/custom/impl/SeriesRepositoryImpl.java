package com.vlasovartem.tvspace.persistence.repository.custom.impl;

import com.vlasovartem.tvspace.entity.Series;
import com.vlasovartem.tvspace.persistence.repository.custom.SeriesRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.stereotype.Repository;

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
        LinkedHashMap hashMap = operations
                .aggregate(aggregation, Series.class, LinkedHashMap.class)
                .getUniqueMappedResult();
        if(Objects.nonNull(hashMap)) {
            if(hashMap.containsKey("genres") && hashMap.get("genres") instanceof ArrayList) {
                List<String> genres = new ArrayList<>((ArrayList<String>) hashMap.get("genres"));
                return genres.stream().sorted(String::compareToIgnoreCase).collect(Collectors.toSet());
            }
        }
        return null;
    }

    @Override
    public Set<Integer> getSeriesYears() {
        Aggregation aggregation = newAggregation(
                group().addToSet("yearStart").as("years"),
                project("years").andExclude("_id")
        );
        LinkedHashMap hashMap = operations.aggregate(aggregation, Series.class, LinkedHashMap.class).getUniqueMappedResult();
        if(Objects.nonNull(hashMap)) {
            if(hashMap.containsKey("years") && hashMap.get("years") instanceof ArrayList) {
                List<Integer> years = new ArrayList<>((ArrayList<Integer>) hashMap.get("years"));
                return years.stream().sorted(Integer::compareTo).collect(Collectors.toSet());
            }
        }
        return null;
    }

}
