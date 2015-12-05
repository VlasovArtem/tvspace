package com.vlasovartem.tvspace;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBList;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.vlasovartem.tvspace.entity.SearchInfo;
import com.vlasovartem.tvspace.entity.Series;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

/**
 * Created by artemvlasov on 25/11/15.
 */
public class MongoTemplateTest {
    public static void main(String[] args) throws IOException {
        MongoOperations operations = new MongoTemplate(new MongoClient(), "pmdb");
        Aggregation aggregation = newAggregation(
                unwind("genres"),
                group().addToSet("genres").as("genres").addToSet("seriesStart").as("years"),
                project("genres").andInclude("years").andExclude("_id")
        );
        SearchInfo info = operations.aggregate(aggregation, Series.class, SearchInfo.class).getUniqueMappedResult();
        System.out.println(info.getYears());
        System.out.println(info.getGenres());
    }
    public static LinkedHashMap convert(BasicDBList objects) {
        return (LinkedHashMap) objects.get(0);
    }
}
