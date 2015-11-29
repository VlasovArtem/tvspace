package com.vlasovartem.tvspace;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBList;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.vlasovartem.tvspace.entity.Series;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

/**
 * Created by artemvlasov on 25/11/15.
 */
public class MongoTemplateTest {
    public static void main(String[] args) throws IOException {
        MongoOperations operations = new MongoTemplate(new MongoClient(), "tvspace");
        Aggregation aggregation = newAggregation(
                unwind("genres"),
                group().addToSet("genres").as("genres"),
                project("genres").andExclude("_id")
        );
        LinkedHashMap object = operations.aggregate(aggregation, Series.class,
                LinkedHashMap.class).getUniqueMappedResult();
        System.out.println(object.get("genres"));
    }
    public static LinkedHashMap convert(BasicDBList objects) {
        return (LinkedHashMap) objects.get(0);
    }
}
