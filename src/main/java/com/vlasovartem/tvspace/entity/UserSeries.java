package com.vlasovartem.tvspace.entity;

import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;

/**
 * Created by artemvlasov on 07/12/15.
 */
public class UserSeries {
    private String seriesId;
    @LastModifiedDate
    private LocalDate modifiedDate;
    private int watchedSeason;
    private int watchedEpisode;

    public UserSeries() {
    }

    public UserSeries(String seriesId, int watchedSeason, int watchedEpisode) {
        this.seriesId = seriesId;
        this.watchedSeason = watchedSeason;
        this.watchedEpisode = watchedEpisode;
    }

    public String getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(String seriesId) {
        this.seriesId = seriesId;
    }

    public LocalDate getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDate modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public int getWatchedSeason() {
        return watchedSeason;
    }

    public void setWatchedSeason(int watchedSeason) {
        this.watchedSeason = watchedSeason;
    }

    public int getWatchedEpisode() {
        return watchedEpisode;
    }

    public void setWatchedEpisode(int watchedEpisode) {
        this.watchedEpisode = watchedEpisode;
    }
}
