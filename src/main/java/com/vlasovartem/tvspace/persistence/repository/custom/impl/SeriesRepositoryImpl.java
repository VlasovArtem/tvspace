package com.vlasovartem.tvspace.persistence.repository.custom.impl;

import com.vlasovartem.tvspace.controller.model.SearchInfo;
import com.vlasovartem.tvspace.entity.Series;
import com.vlasovartem.tvspace.entity.UserSeries;
import com.vlasovartem.tvspace.persistence.repository.UserRepository;
import com.vlasovartem.tvspace.persistence.repository.custom.SeriesRepositoryCustom;
import com.vlasovartem.tvspace.service.SeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Set;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

import static com.vlasovartem.tvspace.utils.security.AuthenticatedUserPrincipalUtil.getAuthenticationId;
import static com.vlasovartem.tvspace.utils.security.AuthenticatedUserPrincipalUtil.isAuthenticated;
import static org.springframework.data.convert.Jsr310Converters.LocalDateToDateConverter.INSTANCE;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

/**
 * Created by artemvlasov on 25/11/15.
 */
@Repository
public class SeriesRepositoryImpl implements SeriesRepositoryCustom {

    private MongoOperations operations;
    private UserRepository userRepository;

    @Autowired
    public SeriesRepositoryImpl(MongoOperations operations, UserRepository userRepository) {
        this.operations = operations;
        this.userRepository = userRepository;
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

    @Override
    public List<Series> findSeries(String genre, Integer year, String title, boolean hideFinished, boolean
            showUserSeries, String sort, String direction) {
        Query query = new Query();
        ArrayList<Criteria> criterias = new ArrayList<>();
        criterias.addAll(Arrays.asList(findByGenre(genre), findByYear(year), findByTitle(title), findByFinished
                (hideFinished), findUserSeries(showUserSeries), findNextEpisodes(sort)));
        criterias.removeIf(Objects::isNull);
        query.addCriteria(new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()])));
        if(Objects.nonNull(sort) && Objects.nonNull(direction))
            query.with(new Sort(Sort.Direction.valueOf(direction), sort));
        if (criterias.isEmpty()) {
            return operations.findAll(Series.class);
        }
        return operations.find(query, Series.class);
    }

    private Criteria findByGenre (String genre) {
        if(Objects.nonNull(genre)) {
            return new Criteria("genres").is(genre);
        }
        return null;
    }

    private Criteria findByTitle(String title) {
        if(Objects.nonNull(title)) {
            return new Criteria("title").regex(title, "i");
        }
        return null;
    }

    private Criteria findByYear (Integer year) {
        if(Objects.nonNull(year)) {
            Date yearStart = INSTANCE.convert(LocalDate.of(year, Month.JANUARY, 1));
            Date yearEnd = INSTANCE.convert(LocalDate.of(year, Month.DECEMBER, 31));
            return new Criteria().andOperator(
                    new Criteria("seriesStart").gte(yearStart),
                    new Criteria("seriesStart").lte(yearEnd));
        }
        return null;
    }

    private Criteria findByFinished (boolean hideFinished) {
        if(hideFinished) {
            return new Criteria("finished").is(false);
        }
        return null;
    }

    private Criteria findUserSeries(boolean showUserSeries) {
        if(showUserSeries) {
            List<String> userSeriesIds = Collections.emptyList();
            if(isAuthenticated()) {
                List<UserSeries> userSeries = userRepository.findOne(getAuthenticationId()).getUserSeries();
                if(Objects.nonNull(userSeries))
                    userSeriesIds = userSeries.stream().map(UserSeries::getSeriesId).collect(Collectors.toList());
            }
            return new Criteria("id").in(userSeriesIds);
        }
        return null;
    }

    private Criteria findNextEpisodes (String sort) {
        if(SeriesService.NEXT_EPISODE_DATE_PROPERTY.equals(sort))
            return new Criteria(SeriesService.NEXT_EPISODE_DATE_PROPERTY)
                    .exists(true)
                    .gte(INSTANCE.convert(LocalDate.now()));
        return null;
    }
}
