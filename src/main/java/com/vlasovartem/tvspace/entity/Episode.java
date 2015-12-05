package com.vlasovartem.tvspace.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

/**
 * Created by artemvlasov on 23/11/15.
 */
public class Episode {

    private String title;
    private LocalDate released;
    private int episode;
    private double imdbRating;
    private String imdbID;

    public String getTitle() {
        return title;
    }

    @JsonProperty("Title")
    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getReleased() {
        return released;
    }

    @JsonProperty("Released")
    public void setReleased(LocalDate released) {
        this.released = released;
    }

    public int getEpisode() {
        return episode;
    }

    @JsonProperty("Episode")
    public void setEpisode(int episode) {
        this.episode = episode;
    }

    public double getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(double imdbRating) {
        this.imdbRating = imdbRating;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    @Override
    public String toString() {
        return "Episode{" +
                "title='" + title + '\'' +
                ", released=" + released +
                ", episode=" + episode +
                ", imdbRating=" + imdbRating +
                ", imdbID='" + imdbID + '\'' +
                '}';
    }
}
