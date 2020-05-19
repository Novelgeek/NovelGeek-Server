package lk.ucsc.NovelGeek.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
    @Id //this will make userId the private key of your table
    @GeneratedValue(strategy = GenerationType.AUTO) //will generate ids automatically if you need it
    private long userId;

    private String name;

    //this contructor is necessary for every model
    //a constructor without parameters
    public User() {
    }

    public User(long userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
