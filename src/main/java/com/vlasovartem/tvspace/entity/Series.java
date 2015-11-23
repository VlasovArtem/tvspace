package com.vlasovartem.tvspace.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by artemvlasov on 20/11/15.
 */
@Document(collection = "series")
public class Series {
    private String id;
    private int yearStart;
    private int yearEnd;
    private double imdbRating;
    private boolean finished;
    private int currentSeason;
    private Episode nextEpisode;
    private String imdbId;
    private String plot;
    private String poster;
    private String country;
    private List<String> writers;
    private List<String> directors;
    private String title;
    private List<String> genres;
    private List<Actor> actors;
    private List<Integer> seasonEpisodeCounts;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getYearStart() {
        return yearStart;
    }

    public void setYearStart(int yearStart) {
        this.yearStart = yearStart;
    }

    public int getYearEnd() {
        return yearEnd;
    }

    public void setYearEnd(int yearEnd) {
        this.yearEnd = yearEnd;
    }

    public double getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(double imdbRating) {
        this.imdbRating = imdbRating;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public int getCurrentSeason() {
        return currentSeason;
    }

    public void setCurrentSeason(int currentSeason) {
        this.currentSeason = currentSeason;
    }

    public Episode getNextEpisode() {
        return nextEpisode;
    }

    public void setNextEpisode(Episode nextEpisode) {
        this.nextEpisode = nextEpisode;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<String> getWriters() {
        return writers;
    }

    public void setWriters(List<String> writers) {
        this.writers = writers;
    }

    public List<String> getDirectors() {
        return directors;
    }

    public void setDirectors(List<String> directors) {
        this.directors = directors;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public List<Integer> getSeasonEpisodeCounts() {
        return seasonEpisodeCounts;
    }

    public void setSeasonEpisodeCounts(List<Integer> seasonEpisodeCounts) {
        this.seasonEpisodeCounts = seasonEpisodeCounts;
    }

    @Override
    public String toString() {
        return "Series{" +
                "id='" + id + '\'' +
                ", yearStart=" + yearStart +
                ", yearEnd=" + yearEnd +
                ", imdbRating=" + imdbRating +
                ", finished=" + finished +
                ", currentSeason=" + currentSeason +
                ", nextEpisode=" + nextEpisode +
                ", imdbId='" + imdbId + '\'' +
                ", plot='" + plot + '\'' +
                ", poster='" + poster + '\'' +
                ", country='" + country + '\'' +
                ", writer='" + writers + '\'' +
                ", directors='" + directors + '\'' +
                ", title='" + title + '\'' +
                ", genres=" + genres +
                ", actors=" + actors +
                '}';
    }
}
