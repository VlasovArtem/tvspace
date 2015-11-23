package com.vlasovartem.tvspace.entity;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by artemvlasov on 20/11/15.
 */
@Document(collection = "actors")
public class Actor {
    private String firstname;
    private String lastname;
    private LocalDate birthday;
    @DBRef
    private List<Actor> actors;

    public Actor(String firstname, String lastname, LocalDate birthday, List<Actor> actors) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthday = birthday;
        this.actors = actors;
    }
}
