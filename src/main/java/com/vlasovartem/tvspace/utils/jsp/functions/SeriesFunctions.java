package com.vlasovartem.tvspace.utils.jsp.functions;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vlasovartem.tvspace.entity.Episode;
import com.vlasovartem.tvspace.entity.Season;
import com.vlasovartem.tvspace.entity.Series;
import com.vlasovartem.tvspace.entity.UserSeries;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Objects.*;

/**
 * Created by artemvlasov on 05/12/15.
 */
public class SeriesFunctions {

    /**
     * Join string with delimiter ', '
     * @param strings Arrays of strings
     * @return String that join with help of specified delimiter
     */
    public static String joining(List<String> strings) {
        if(nonNull(strings))
            return strings.stream().collect(Collectors.joining(", "));
        return null;
    }

    /**
     * Check episode date
     * @param episodeDate episode date
     * @return true if episode date is equals to today date or date is after today, otherwise return false
     */
    public static boolean checkNextEpisode(LocalDate episodeDate) {
        return nonNull(episodeDate) &&
                (episodeDate.isAfter(LocalDate.now()) || episodeDate.isEqual(LocalDate.now()));
    }

    /**
     * Check provided series id that contains in userSeries list
     * @param seriesId series id
     * @param userSeries user series list
     * @return true if list of user series is contains series id, otherwise false.
     */
    public static boolean checkUserSeries (String seriesId, List<UserSeries> userSeries) {
        return nonNull(userSeries) && nonNull(seriesId) && userSeries.stream().anyMatch(us -> seriesId.equals(us.getSeriesId()));
    }

    /**
     * Find User Series with applied series id
     * @param seriesId series id
     * @param userSeries user series
     * @return {@link UserSeries} if series id has any matches in user series id, otherwise null
     */
    public static UserSeries findUserSeries (String seriesId, List<UserSeries> userSeries) {
        if(nonNull(userSeries) && nonNull(seriesId))
            return userSeries.stream().filter(us -> seriesId.equals(us.getSeriesId())).findFirst().orElse(null);
        return null;
    }

    /**
     * Find season for select option if user is authenticated. If series id is matches with series in user series
     * list, than method will return number of the season from user series, otherwise method will search for season
     * number inside series object. If Series object contains link to the next episode in this case methid will return
     * number of this season. If Series does not contains link to the next episode, method will return the length of
     * the series seasons, which is matches to next season number.
     * @param series series object
     * @param userSeries list of user series
     * @return number of season
     */
    public static int findSeasonToSelect(Series series, List<UserSeries> userSeries) {
        UserSeries us = findUserSeries(series.getId(), userSeries);
        if(isNull(us)) {
            if(nonNull(series.getNextEpisode())) {
                return series.getNextEpisode().getSeasonNumber();
            }
            return series.getSeasons().size();
        }
        return us.getWatchedSeason();
    }

    /**
     * Find number of episode with provided season number if authenticated user is not contains user series with
     * provided, than method will find episode from next episode link if it is not null, otherwise method will find
     * the closest episode from today.
     * @param series checked series
     * @param seasonNumber season number
     * @param userSeries list of user series
     * @return number of episode
     */
    public static int findSeasonEpisodeToSelect(Series series, int seasonNumber, List<UserSeries> userSeries) {
        UserSeries us = findUserSeries(series.getId(), userSeries);
        if(isNull(us)) {
            if(nonNull(series.getNextEpisode())) {
                return series.getNextEpisode().getEpisodeNumber();
            } else {
                Episode episodeFromLastSeason = series.getSeasons().stream()
                        .filter(s -> s.getSeasonNumber() == seasonNumber).findFirst().get().getEpisodes().stream()
                        .filter(e -> nonNull(e.getEpisodeDate())
                                && (e.getEpisodeDate().isAfter(LocalDate.now())
                                || e.getEpisodeDate().equals(LocalDate.now()))).findFirst().orElse(null);
                if(isNull(episodeFromLastSeason) && seasonNumber != 1) {
                    Episode episodeFromPreviousSeason = series.getSeasons().stream()
                            .filter(s -> s.getSeasonNumber() == seasonNumber - 1).findFirst().get().getEpisodes()
                            .stream()
                            .filter(e -> nonNull(e.getEpisodeDate())
                                    && (e.getEpisodeDate().isAfter(LocalDate.now())
                                    || e.getEpisodeDate().equals(LocalDate.now()))).findFirst().orElse(null);
                    if(isNull(episodeFromPreviousSeason)) {
                        return 1;
                    } else {
                        return episodeFromPreviousSeason.getEpisodeNumber();
                    }
                } else if (seasonNumber == 1) {
                    return 1;
                } else {
                    episodeFromLastSeason.getEpisodeDate();
                }
            }
        }
        return us.getWatchedEpisode();
    }

    /**
     * Find amount of episodes in particular season
     * @param series series link
     * @param seasonNumber season number
     * @return number of episodes
     */
    public static int findSeasonEpisodeLength(Series series, int seasonNumber) {
        Season season = series.getSeasons().stream().filter(s -> s.getSeasonNumber() == seasonNumber).findFirst()
                .orElse(null);
        if(nonNull(season) && nonNull(season.getEpisodes())) {
            return season.getEpisodes().size();
        }
        return 0;
    }

    /**
     * Create series map with season nubmer as key and the amout of episodes as value.
     * @param series series object
     * @return ObjectNode in format key/value
     */
    public static ObjectNode seriesSeasonMap(Series series) {
        if(nonNull(series) && nonNull(series.getSeasons())) {
            ObjectNode nodes = JsonNodeFactory.instance.objectNode();
            for (Season season : series.getSeasons().stream()
                    .sorted(Comparator.comparingInt(Season::getSeasonNumber))
                    .collect(Collectors.toList())) {
                if(nonNull(season.getEpisodes())) {
                    nodes.put(String.valueOf(season.getSeasonNumber()), season.getEpisodes().size());
                } else {
                    nodes.put(String.valueOf(season.getSeasonNumber()), 0);
                }
            }
            return nodes;
        }
        return null;
    }
}
