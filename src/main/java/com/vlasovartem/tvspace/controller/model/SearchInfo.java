package com.vlasovartem.tvspace.controller.model;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by artemvlasov on 05/12/15.
 */
public class SearchInfo {
    private List<LocalDate> years;
    private List<String> genres;

    public List<LocalDate> getYears() {
        return years;
    }

    public void setYears(List<LocalDate> years) {
        this.years = years;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }
}
