package com.vlasovartem.tvspace.controller.model;

/**
 * Created by artemvlasov on 26/11/15.
 */
public class Search {
    private String genre;
    private Integer year;
    private String title;
    private boolean hideFinished;
    private String sort;
    private String direction;

    public Search(String genre, Integer year, String title, boolean hideFinished, String sort, String direction) {
        this.genre = genre;
        this.year = year;
        this.title = title;
        this.hideFinished = hideFinished;
        this.sort = sort;
        this.direction = direction;
    }

    public Search(String sort, String direction) {
        this.sort = sort;
        this.direction = direction;
    }

    public Search() {
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isHideFinished() {
        return hideFinished;
    }

    public void setHideFinished(boolean hideFinished) {
        this.hideFinished = hideFinished;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
