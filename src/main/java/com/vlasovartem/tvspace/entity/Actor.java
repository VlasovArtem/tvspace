package com.vlasovartem.tvspace.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Set;

/**
 * Created by artemvlasov on 20/11/15.
 */
@Document(collection = "actors")
public class Actor {
    private String id;
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    private Set<String> seriesIds;

    public Actor() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Set<String> getSeriesIds() {
        return seriesIds;
    }

    public void setSeriesIds(Set<String> seriesIds) {
        this.seriesIds = seriesIds;
    }
}
