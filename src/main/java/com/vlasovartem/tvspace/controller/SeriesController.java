package com.vlasovartem.tvspace.controller;

import com.vlasovartem.tvspace.controller.model.Search;
import com.vlasovartem.tvspace.entity.Series;
import com.vlasovartem.tvspace.persistence.repository.SeriesRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created by artemvlasov on 23/11/15.
 */
@Controller
@RequestMapping(path = "/series")
public class SeriesController {

    private SeriesRepository repository;
    private final List<String> sortProperties;
    private final String initSortName = "Imdb Rating";
    private final String initSortDirection = "DESC";

    @Autowired
    public SeriesController (@Qualifier("seriesRepository") SeriesRepository repository) {
        this.repository = repository;
        sortProperties = Arrays.asList("Year Start", "Year End", "Imdb Rating", "Finished", "Title");
    }

    @RequestMapping
    public ModelAndView getAll () {
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("genres", seriesGenres());
        modelMap.put("search", new Search(initSortName, initSortDirection));
        modelMap.put("years", seriesYears());
        modelMap.put("sortProperties", sortProperties);
        String initSortProperty = convertSortProperty(initSortName);
        modelMap.put("series", repository.findAll(new Sort(Sort.Direction.valueOf(initSortDirection), initSortProperty)));
        return new ModelAndView("/series/series", modelMap);
    }

    @RequestMapping(path = "/search", method = GET)
    public ModelAndView search (@ModelAttribute("search") Search search,
                                @RequestParam(required = false) String genre,
                                @RequestParam(required = false) Integer year,
                                @RequestParam(required = false) String title,
                                @RequestParam(required = false, defaultValue = "false") boolean hideFinished,
                                @RequestParam(required = false, defaultValue = "Imdb Rating") String sort,
                                @RequestParam(required = false, defaultValue = "DESC") String direction) {
        Sort documentSort = new Sort(Sort.Direction.fromString(direction), convertSortProperty(sort));
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("genres", seriesGenres());
        modelMap.put("search", search);
        modelMap.put("sortProperties", sortProperties);
        modelMap.put("years", seriesYears());
        List<Series> series = null;
        if(Objects.isNull(genre) && Objects.isNull(year) && Objects.isNull(title) && !hideFinished && initSortName
                .equals(sort) && initSortDirection.equals(direction)) {
            return new ModelAndView("redirect:/series");
        } else if (Objects.isNull(genre) &&
                Objects.isNull(year) &&
                Objects.isNull(title) && (!initSortName.equals(sort) || !initSortDirection.equals(direction))) {
            if(hideFinished) {
                series = repository.findByFinishedIsFalse(documentSort);
            } else {
                series = repository.findAll(documentSort);
            }
        } else {
            series = findSeries (genre, year, title, hideFinished, documentSort);
        }
        modelMap.put("series", series);
        return new ModelAndView("/series/series", modelMap);
    }

    /**
     * Find series by passed parameters
     * @param genre Genre of the series
     * @param year Year of the series
     * @param title Title of the series
     * @param hideFinished hide finished
     * @return List<Series> when it`s match specific parameters
     */
    public List<Series> findSeries (String genre, Integer year, String title, boolean hideFinished, Sort sort) {
        if (Objects.isNull(genre) && Objects.isNull(year) && Objects.isNull(title) && hideFinished) {
            return repository.findByFinishedIsFalse(sort);
        } else if (Objects.nonNull(genre) && Objects.nonNull(year) && year != 0 && Objects.nonNull(title)) {
            if (hideFinished) return repository.findByGenresAndYearStartAndTitleLikeIgnoreCaseAndFinishedIsFalse
                    (genre, year, title, sort);
            else return repository.findByGenresAndYearStartAndTitleLikeIgnoreCase (genre, year, title, sort);
        } else if (Objects.nonNull(genre) && Objects.nonNull(year)) {
            if (hideFinished) return repository.findByGenresAndYearStartAndFinishedIsFalse (genre, year, sort);
            else return repository.findByGenresAndYearStart (genre, year, sort);
        } else if (Objects.nonNull(genre) && Objects.nonNull(title)) {
            if (hideFinished) return repository.findByGenresAndTitleLikeIgnoreCaseAndFinishedIsFalse (genre, title, sort);
            else return repository.findByGenresAndTitleLikeIgnoreCase (genre, title, sort);
        } else if (Objects.nonNull(year) && Objects.nonNull(title)) {
            if (hideFinished) return repository.findByYearStartAndTitleLikeIgnoreCaseAndFinishedIsFalse (year, title, sort);
            else return repository.findByYearStartAndTitleLikeIgnoreCase(year, title, sort);
        } else if (Objects.isNull(year) && Objects.isNull(genre)) {
            if (hideFinished) return repository.findByTitleLikeIgnoreCaseAndFinishedIsFalse(title, sort);
            else return repository.findByTitleLikeIgnoreCase(title, sort);
        } else {
            if (hideFinished) return repository.findByGenresOrYearStartAndFinishedIsFalse(genre, year == null ? 0 : year, sort);
            else return repository.findByGenresOrYearStart(genre, year == null ? 0 : year, sort);
        }
    }

    /**
     * Collect set of  Genre of all Series
     * @return set of genres
     */
    public Set<String> seriesGenres () {
        return repository.getSeriesGenres();
    }

    /**
     * Collect set of Year of all Series
     * @return set of years
     */
    public Set<Integer> seriesYears () {
        return repository.getSeriesYears();
    }

    public String convertSortProperty (String property) {
        if(StringUtils.containsWhitespace(property)) {
            String[] propertyWords = property.split("\\s");
            StringBuilder builder = new StringBuilder();
            for(int i = 0; i < propertyWords.length; i++) {
                if(i == 0) {
                    builder.append(propertyWords[i].toLowerCase());
                } else {
                    builder.append(StringUtils.capitalize(propertyWords[i]));
                }
            }
            return builder.toString();
        } else {
            return property.toLowerCase();
        }
    }
}
