package com.vlasovartem.tvspace.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.mongodb.MongoClient;
import com.vlasovartem.tvspace.entity.Actor;
import com.vlasovartem.tvspace.entity.Episode;
import com.vlasovartem.tvspace.entity.Series;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by artemvlasov on 22/11/15.
 * Parse series and actors data and save it to database.
 */
public class SeriesParser {
    public static Logger logger = Logger.getLogger(SeriesParser.class.getName());
    private static List<String> seriesJsonFields = Arrays.asList("Title", "Year", "Genre", "Director", "Writer",
            "Actors", "Plot", "Country", "Poster", "imdbRating", "imdbID");
    private static List<String> ignoredFields = Arrays.asList("Year", "Actors", "imdbRating", "Genre", "Writer",
            "Director");
    private static MongoOperations mongoTemplate;
    static {
        try {
            mongoTemplate = new MongoTemplate(new MongoClient(), "tvspace");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        File file = new File("parsedSeries.txt");
        if(file.exists()) {
            FileUtils.readLines(file)
                    .stream()
                    .distinct()
                    .filter(s -> !mongoTemplate.exists(Query.query(Criteria.where("title").is(s)), Series.class))
                    .forEach(SeriesParser::parseData);
        }
    }

    /**
     * Parse data that will be collected after OMDB API response.
     * @param seriesName the name of the series for which the information is collected
     */
    public static void parseData(String seriesName) {
        seriesName = Objects.requireNonNull(seriesName, "Series name cannot be null");
        try {
            JsonNode parsedSeriesJson = parseSeriesJson(seriesName);
            Function<Series, Series> saveSeries = o -> saveObject(o, "title", o.getTitle(), Series.class);
            Function<Actor, Actor> saveActor = a -> saveObject(a, "name", a.getName(), Actor.class);
            Series series = saveSeries.apply(parseSeries(parsedSeriesJson));
            series.setActors(
                    parseActors(series.getId(), parsedSeriesJson.findValue("Actors").asText(),
                            mongoTemplate.findAll(Actor.class))
                            .stream()
                            .map(s -> {
                                Actor savedActor = saveActor.apply(s);
                                savedActor.setSeriesIds(null);
                                return savedActor;
                            })
                            .collect(Collectors.toList()));
            updateSeasonSeriesInformation(series);
            mongoTemplate.updateFirst(
                    Query.query(Criteria.where("title").is(series.getTitle())),
                    Update.update("actors", series.getActors())
                            .set("nextEpisode", series.getNextEpisode())
                            .set("seasonEpisodeCounts", series.getSeasonEpisodeCounts())
                            .set("currentSeason", series.getCurrentSeason()),
                    Series.class);
        } catch (RuntimeException e) {
            e.printStackTrace();
            logger.info(String.format("Series %s is not found", seriesName));
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Parse JSON from OMDB API url
     * @param seriesName name of the series
     * @return JsonNode object parsed from url
     * @throws IOException if URL is invalid
     */
    private static JsonNode parseSeriesJson(String seriesName) throws IOException {
        String convertedUrl = String.format("http://www.omdbapi.com/?t=%s&y=&plot=short&r=json&type=series", URLEncoder
                .encode
                        (seriesName, "UTF-8"));
        return new ObjectMapper().readTree(new URL(convertedUrl));
    }

    /**
     * Parse series genre
     * @param object series genres
     * @return List of the genres
     */
    private static List<String> parseString(String object) {
        if(Objects.nonNull(object) && !"N/A".equals(object)) {
            return Arrays.stream(object.split(", ")).collect(Collectors.toList());
        }
        return null;
    }

    /**
     * Parse all related Series data
     * @param node JsonNode object
     * @return Mapped Series
     */
    private static Series parseSeries(JsonNode node) {
        Series series = new Series();
        parseYear(series, node.findValue("Year").asText());
        series.setImdbRating(node.findValue("imdbRating").asDouble());
        series.setGenres(parseString(node.findValue("Genre").asText()));
        series.setWriters(parseString(node.findValue("Writer").asText()));
        series.setDirectors(parseString(node.findValue("Director").asText()));
        parseSeriesContent(series, node, seriesJsonFields.stream().filter(s -> !ignoredFields.contains(s)).collect
                (Collectors
                        .toList()));
        return series;
    }

    /**
     * Parse content that related to Series Bean
     * @param series Series object that should be updated
     * @param node JsonNode object
     * @param filteredJsonNames List of the properties that should be mapped from json to Series bean
     */
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

    /**
     * Parse year from object (start and end year)
     * @param series Updated Series
     * @param year Unconverted year object
     */
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

    /**
     * Parse actors from series json
     * @param seriesId Id of Series, that related to parsed actors
     * @param actors Unconverted actors object
     * @param savedActors List of actors, that already saved in database
     * @return list of actors with updated list of series ids that associated with parsed actors
     */
    private static List<Actor> parseActors(String seriesId, String actors, List<Actor> savedActors) {
        return Arrays.stream(actors.split(", ")).map(name -> {
            List<Actor> filteredActors = savedActors
                    .stream()
                    .filter(a -> name.equals(a.getName()))
                    .collect(Collectors.toList());
            Actor actor = null;
            if(filteredActors.size() > 0) {
                actor = filteredActors.get(0);
            } else {
                actor = new Actor();
            }
            actor.setName(name);
            if (Objects.nonNull(actor.getSeriesIds())) {
                actor.getSeriesIds().add(seriesId);
            } else {
                actor.setSeriesIds(Collections.singleton(seriesId));
            }
            return actor;
        }).collect(Collectors.toList());

    }

    /**
     * Save object to database and return saved object with generated id
     * @param object Object that will be saved
     * @param criteriaKey key that used to find save object
     * @param criteriaValue value that associated with key
     * @param clazz Class of the saved Object
     * @param <T> Generic returned value
     * @return return generic object if it was successfully saved
     */
    private static <T> T saveObject(T object, String criteriaKey, Object criteriaValue, Class<T> clazz) {
        mongoTemplate.save(object);
        return mongoTemplate.findOne(Query.query(Criteria.where(criteriaKey).is(criteriaValue)), clazz);
    }

    /**
     * Update season series information, find last season and find next episode
     * @param series Update series
     * @throws IOException if method cannot parse json from url
     */
    public static void updateSeasonSeriesInformation(Series series) throws IOException {
        List<Integer> seasonsEpisodesCount = new ArrayList<>();
        while (true) {
            JsonNode node = parseSeasonSeriesJson(series.getTitle(), seasonsEpisodesCount.size() + 1);
            if(Objects.nonNull(node)) {
                if (Boolean.valueOf(node.findValue("Response").asText().toLowerCase())) {
                    seasonsEpisodesCount.add(node.findValue("Episodes").size());
                } else break;
            } else {
                break;
            }
        }
        JsonNode node = parseSeasonSeriesJson(series.getTitle(), seasonsEpisodesCount.size());
        series.setSeasonEpisodeCounts(seasonsEpisodesCount);
        series.setCurrentSeason(seasonsEpisodesCount.size());
        if(Objects.nonNull(node)) {
            series.setNextEpisode(findNextEpisode(node.findValue("Episodes")));
        }
    }

    /**
     * Find next episode for series
     * @param episodesNode Unconverted episodes node
     * @return First filtered episode if last it`s exists otherwise return null
     */
    private static Episode findNextEpisode(JsonNode episodesNode) {
        final ObjectMapper mapper = new ObjectMapper();
        return Lists.newArrayList(episodesNode.elements())
                .stream()
                .map(node -> {
                    try {
                        return mapper.readValue(node.toString(), Episode.class);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }})
                .filter(e ->
                        Objects.nonNull(e) && Objects.nonNull(e.getReleased()) && LocalDate.now().isBefore(e.getReleased()))
                .findFirst().orElse(null);
    }

    /**
     * Parse json from OMDB Api associated with series title and season number
     * @param seriesTitle Series title
     * @param seasonNumber Season number
     * @return @{link JsonNode} if season info is successfully found otherwise return null
     * @throws IOException if URL is not valid
     */
    private static JsonNode parseSeasonSeriesJson(String seriesTitle, int seasonNumber) throws IOException {
        String convertedUrl = String.format("http://www.omdbapi.com/?t=%s&type=series&Season=%d", URLEncoder
                .encode(seriesTitle, "UTF-8"), seasonNumber);
        JsonNode node = new ObjectMapper().readTree(new URL(convertedUrl));
        return Boolean.valueOf(node.findValue("Response").asText().toLowerCase()) ? node : null;
    }
}
