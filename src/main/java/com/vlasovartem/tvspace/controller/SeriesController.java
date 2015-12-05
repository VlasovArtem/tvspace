package com.vlasovartem.tvspace.controller;

import com.vlasovartem.tvspace.controller.model.Search;
import com.vlasovartem.tvspace.entity.Series;
import com.vlasovartem.tvspace.persistence.repository.SeriesRepository;
import com.vlasovartem.tvspace.service.SeriesService;
import org.apache.commons.lang3.StringUtils;
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

    private SeriesService seriesService;
    private final List<String> sortProperties;
    private final String initSortName = "Imdb Rating";
    private final String initSortDirection = "DESC";

    @Autowired
    public SeriesController (SeriesService seriesService) {
        this.seriesService = seriesService;
        sortProperties = Arrays.asList("Year Start", "Year End", "Imdb Rating", "Finished", "Title");
    }

    @RequestMapping
    public ModelAndView getAll () {
        String initSortProperty = convertSortProperty(initSortName);
        return new ModelAndView("/series/series",
                prepareModelMap(
                        new Search(initSortName, initSortDirection),
                        seriesService.findAll(new Sort(Sort.Direction.valueOf(initSortDirection), initSortProperty))));
    }

    @RequestMapping(path = "/search", method = GET)
    public ModelAndView search (@ModelAttribute("search") Search search,
                                @RequestParam(required = false) String genre,
                                @RequestParam(required = false) Integer year,
                                @RequestParam(required = false) String title,
                                @RequestParam(required = false, defaultValue = "false") boolean hideFinished,
                                @RequestParam(required = false, defaultValue = "Imdb Rating") String sort,
                                @RequestParam(required = false, defaultValue = "DESC") String direction) {
        updateSearch(search, genre, year, title, hideFinished, sort, direction);
        Sort documentSort = new Sort(Sort.Direction.fromString(direction), convertSortProperty(sort));
        List<Series> series;
        if(Objects.isNull(genre) && Objects.isNull(year) && Objects.isNull(title) && !hideFinished && initSortName
                .equals(sort) && initSortDirection.equals(direction)) {
            return new ModelAndView("redirect:/series");
        } else if (Objects.isNull(genre) &&
                Objects.isNull(year) &&
                Objects.isNull(title) &&
                (!initSortName.equals(sort) || !initSortDirection.equals(direction))) {
            series = hideFinished ? seriesService.findFinished(documentSort) : seriesService.findAll(documentSort);
        } else {
            series = seriesService.findSeries(genre, year, title, hideFinished, documentSort);
        }
        return new ModelAndView("/series/series",
                prepareModelMap(
                        search, series));
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

    private void updateSearch(Search search, String genre, Integer year,
                              String title, boolean hideFinished, String sort,
                              String direction) {
        search.setDirection(direction);
        search.setGenre(genre);
        search.setHideFinished(hideFinished);
        search.setSort(sort);
        search.setTitle(title);
        search.setYear(year);
    }

    private Map<String, Object> prepareModelMap (Search search, List<Series> series) {
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("genres", seriesService.findSeriesGenres());
        modelMap.put("search", search);
        modelMap.put("years", seriesService.findSeriesYears());
        modelMap.put("sortProperties", sortProperties);
        modelMap.put("series", series);
        return modelMap;
    }

}
