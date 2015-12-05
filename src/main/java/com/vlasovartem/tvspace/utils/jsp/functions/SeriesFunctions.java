package com.vlasovartem.tvspace.utils.jsp.functions;

import java.time.LocalDate;
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
        return Objects.nonNull(episodeDate) && episodeDate.isAfter(LocalDate.now());
    }
}
