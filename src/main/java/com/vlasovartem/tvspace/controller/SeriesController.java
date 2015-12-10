package com.vlasovartem.tvspace.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.vlasovartem.tvspace.controller.model.Search;
import com.vlasovartem.tvspace.entity.Series;
import com.vlasovartem.tvspace.entity.UserSeries;
import com.vlasovartem.tvspace.service.SeriesService;
import com.vlasovartem.tvspace.service.UserService;
import com.vlasovartem.tvspace.utils.view.SeriesView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

import static com.vlasovartem.tvspace.service.SeriesService.*;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by artemvlasov on 23/11/15.
 */
@Controller
@RequestMapping(path = "/series")
public class SeriesController {

    private SeriesService seriesService;
    private UserService userService;
    private final List<String> sortProperties;

    @Autowired
    public SeriesController (SeriesService seriesService, UserService userService) {
        this.seriesService = seriesService;
        this.userService = userService;
        sortProperties = Arrays.asList("Year Start", "Year End", "Imdb Rating", "Finished", "Title", "Next Episode");
    }

    @ModelAttribute("genres")
    public Set<String> getGenres() {
        return seriesService.findSeriesGenres();
    }

    @ModelAttribute("years")
    public Set<Integer> getYears () {
        return seriesService.findSeriesYears();
    }

    @ModelAttribute("sortProperties")
    public List<String> getSortProperties () {
        return sortProperties;
    }

    @ModelAttribute("userSeries")
    public List<UserSeries> getUserSeries() {
        return userService.getUserSeries();
    }


    @RequestMapping
    @JsonView(SeriesView.ShortInfoView.class)
    public ModelAndView getAll () {
        String initSortProperty = convertSortProperty(INIT_SORT_NAME);
        return new ModelAndView("/series/series",
                prepareModelMap(new Search(INIT_SORT_NAME, INIT_SORT_DIRECTION),
                        seriesService.findAll(new Sort(Sort.Direction.valueOf(INIT_SORT_DIRECTION), initSortProperty))));
    }

    @RequestMapping(path = "/search", method = GET)
    @JsonView(SeriesView.ShortInfoView.class)
    public ModelAndView search (@ModelAttribute("search") Search search,
                                @RequestParam(required = false) String genre,
                                @RequestParam(required = false) Integer year,
                                @RequestParam(required = false) String title,
                                @RequestParam(required = false, defaultValue = "false") boolean hideFinished,
                                @RequestParam(required = false, defaultValue = "false") boolean showUserSeries,
                                @RequestParam(required = false, defaultValue = "Imdb Rating") String sort,
                                @RequestParam(required = false, defaultValue = "DESC") String direction) {
        sort = "Next Episode".equals(search.getSort()) ? NEXT_EPISODE_DATE_PROPERTY : search.getSort();
        Sort documentSort = new Sort(Sort.Direction.fromString(search.getDirection()), NEXT_EPISODE_DATE_PROPERTY
                .equals(sort) ? sort : convertSortProperty(sort));
        Map<String, Object> modelMap;
        if (Objects.isNull(genre) && Objects.isNull(year) && Objects.isNull(title) && !hideFinished && INIT_SORT_NAME
                .equals(sort) && INIT_SORT_DIRECTION.equals(direction)) {
            return new ModelAndView("redirect:/series");
        } else if(NEXT_EPISODE_DATE_PROPERTY.equals(sort)) {
            modelMap = prepareModelMap(search, seriesService.findNextEpisodes(documentSort));
        } else if (Objects.isNull(genre) &&
                Objects.isNull(year) &&
                Objects.isNull(title) &&
                (!INIT_SORT_NAME.equals(sort) || !INIT_SORT_DIRECTION.equals(direction))) {
            modelMap = prepareModelMap(search, hideFinished ? seriesService.findFinished(documentSort) : seriesService
                    .findAll(documentSort));
        } else {
            modelMap = prepareModelMap(search, seriesService.findSeries(genre, year, title, hideFinished,
                    documentSort));
        }
        return new ModelAndView("/series/series", modelMap);
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

    private Map<String, Object> prepareModelMap (Search search, List<Series> series) {
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("search", search);
        modelMap.put("series", series);
        return modelMap;
    }
}
