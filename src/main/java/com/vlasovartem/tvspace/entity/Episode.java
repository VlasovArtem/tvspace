package com.vlasovartem.tvspace.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.vlasovartem.tvspace.utils.json.EpisodeImdbRatingDeserializer;
import com.vlasovartem.tvspace.utils.json.EpisodeReleasedDeserializer;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * Created by artemvlasov on 23/11/15.
 */
@JsonAutoDetect
public class Episode {

    private String title;
    @JsonDeserialize(using = EpisodeReleasedDeserializer.class)
    private LocalDate released;
    private int episode;
    @JsonDeserialize(using = EpisodeImdbRatingDeserializer.class)
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
