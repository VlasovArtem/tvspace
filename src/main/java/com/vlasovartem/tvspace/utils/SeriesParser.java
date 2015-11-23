package com.vlasovartem.tvspace.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.vlasovartem.tvspace.entity.Series;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by artemvlasov on 22/11/15.
 */
public class SeriesParser {
    public static Logger logger = Logger.getLogger(SeriesParser.class.getName());
    private static List<String> seriesJsonFields = Arrays.asList("Title", "Year", "Genre", "Director", "Writer",
            "Actors", "Plot", "Country", "Poster", "imdbRating", "imdbID");
    private static List<String> ignoredFields = Arrays.asList("Year", "Actors", "imdbRating");
    public static void main(String[] args) throws IOException {
        MongoOperations mongoTemplate = new MongoTemplate(new MongoClient(), "tvspace");
        File file = new File("parsedSeries.txt");
        if(file.exists()) {
            Set<String> seriesNames = FileUtils.readLines(file).stream().collect(Collectors.toSet());
            Set<String> genres = new HashSet<>();
            ObjectMapper mapper = new ObjectMapper();
            for (String seriesName : seriesNames) {
                try {
                    mongoTemplate.save(parseSeries(parseJson(seriesName, mapper)));
                } catch (RuntimeException e) {
                    logger.info(String.format("Series %s in not found", seriesName));
                }
            }
            FileUtils.write(new File("parsedGenres.txt"), genres.stream().collect(Collectors.joining(", ")));
        }
    }

    public static JsonNode parseJson(String seriesName, ObjectMapper mapper) throws IOException {
        String convertedUrl = String.format("http://www.omdbapi.com/?t=%s&y=&plot=full&r=json", URLEncoder.encode
                (seriesName, "UTF-8"));
        return mapper.readTree(new URL(convertedUrl));
    }

    public static void parseGenres(Set<String> genres, JsonNode node) {
        if(Objects.nonNull(node) && Objects.nonNull(genres)) {
            if(Boolean.valueOf(node.findValue("Response").asText().toLowerCase())) {
                String parsedGenres = node.findValue("String").asText();
                genres.addAll(Arrays.stream(parsedGenres.split(", "))
                        .map(String::toUpperCase)
                        .collect(Collectors.toSet()));
            } else {
                throw new RuntimeException();
            }
        }
    }

    public static Series parseSeries(JsonNode node) {
        Series series = new Series();
        parseYear(series, node.findValue("Year").asText());
        series.setImdbRating(node.findValue("imdbRating").asDouble());
        parseSeriesContent(series, node, seriesJsonFields.stream().filter(s -> !ignoredFields.contains(s)).collect
                (Collectors
                .toList()));
        return series;
    }

    private static void parseSeriesContent(Series series, JsonNode node, List<String> filteredJsonNames) {
        BeanWrapper seriesWrapper = new BeanWrapperImpl(series);
        List<String> beanPropNames = filteredJsonNames.stream().map(s -> {
            if("imdbID".equals(s)) {
                return "imdbId";
            } else {
                return s.toLowerCase();
            }
        }).collect(Collectors.toList());
        for (PropertyDescriptor descriptor : BeanUtils.getPropertyDescriptors(series.getClass())) {
            String propName = descriptor.getName();
            if(beanPropNames.contains(propName)) {
                seriesWrapper.setPropertyValue(propName,
                        node.findValue(filteredJsonNames.get(beanPropNames.indexOf(propName))).asText());
            }
        }
    }

    private static void parseYear(Series series, String year) {
        Pattern pattern = Pattern.compile("[0-9]{4}");
        Matcher matcher = pattern.matcher(year);
        int count = 0;
        while(matcher.find()) {
            if(count == 0) {
                series.setYearStart(Integer.parseInt(matcher.group()));
            } else {
                series.setYearEnd(Integer.parseInt(matcher.group()));
            }
            count++;
        }
    }
}
