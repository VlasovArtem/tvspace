package com.vlasovartem.tvspace.utils.jsp.functions;

import com.fasterxml.jackson.databind.JsonNode;
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

/**
 * Created by artemvlasov on 05/12/15.
 */
public class SeriesFunctions {
    public static String joining(List<String> strings) {
        return strings.stream().collect(Collectors.joining(", "));
    }
    public static boolean checkNextEpisode(LocalDate episodeDate) {
        return Objects.nonNull(episodeDate) &&
                (episodeDate.isAfter(LocalDate.now()) || episodeDate.isEqual(LocalDate.now()));
    }
    public static boolean checkUserSeries (String seriesId, List<UserSeries> userSeries) {
        if(Objects.nonNull(userSeries) && Objects.nonNull(seriesId)) {
            return userSeries.stream().anyMatch(us -> seriesId.equals(us.getSeriesId()));
        }
        return false;
    }
    public static UserSeries findUserSeries (String seriesId, List<UserSeries> userSeries) {
        if(Objects.nonNull(userSeries) && Objects.nonNull(seriesId)) {
            return userSeries.stream().filter(us -> seriesId.equals(us.getSeriesId())).findFirst().orElse(null);
        }
        return null;
    }
    public static int findSeasonToSelect(Series series, List<UserSeries> userSeries) {
        UserSeries us = findUserSeries(series.getId(), userSeries);
        if(Objects.isNull(us)) {
            if(Objects.nonNull(series.getNextEpisode())) {
                return series.getNextEpisode().getSeasonNumber();
            } else {
                return series.getSeasons().size();
            }
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
        if(Objects.isNull(us)) {
            if(Objects.nonNull(series.getNextEpisode())) {
                return series.getNextEpisode().getEpisodeNumber();
            } else {
                Episode episodeFromLastSeason = series.getSeasons().stream()
                        .filter(s -> s.getSeasonNumber() == seasonNumber).findFirst().get().getEpisodes().stream()
                        .filter(e -> Objects.nonNull(e.getEpisodeDate())
                                && (e.getEpisodeDate().isAfter(LocalDate.now())
                                || e.getEpisodeDate().equals(LocalDate.now()))).findFirst().orElse(null);
                if(Objects.isNull(episodeFromLastSeason) && seasonNumber != 1) {
                    Episode episodeFromPreviousSeason = series.getSeasons().stream()
                            .filter(s -> s.getSeasonNumber() == seasonNumber - 1).findFirst().get().getEpisodes()
                            .stream()
                            .filter(e -> Objects.nonNull(e.getEpisodeDate())
                                    && (e.getEpisodeDate().isAfter(LocalDate.now())
                                    || e.getEpisodeDate().equals(LocalDate.now()))).findFirst().orElse(null);
                    if(Objects.isNull(episodeFromPreviousSeason)) {
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
    public static int findSeasonEpisodeLength(Series series, int seasonNumber) {
        return series.getSeasons().stream().filter(s -> s.getSeasonNumber() == seasonNumber).findFirst().get().getEpisodes()
                .size();
    }
    public static ObjectNode seriesSeasonMap(Series series) {
        ObjectNode nodes = JsonNodeFactory.instance.objectNode();
        for (Season season : series.getSeasons().stream()
                .sorted(Comparator.comparingInt(Season::getSeasonNumber))
                .collect(Collectors.toList())) {
            nodes.put(String.valueOf(season.getSeasonNumber()), season.getEpisodes().size());
        }
        return nodes;
    }
}
