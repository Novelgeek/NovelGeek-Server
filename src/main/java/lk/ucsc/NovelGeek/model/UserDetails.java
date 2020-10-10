package lk.ucsc.NovelGeek.model;

import javax.persistence.*;

//use this model to store userdetails such as phone no,fname,lname bla bla bla modify it accordingly
//and make a foreign key to the Users entity

@Entity
public class UserDetails {
    @Id //this will make userId the private key of your table
    @GeneratedValue(strategy = GenerationType.AUTO) //will generate ids automatically if you need it


    private long userId;
    private String name;
    private String contact;
    private String city;
    private String Country;
    private String description;

    @OneToOne
    @JoinColumn(name="id" )
    Users user;

    //this contructor is necessary for every model
    //a constructor without parameters
    public UserDetails() {
    }

    public UserDetails(long userId, String name, String contact, String city, String country, String description) {
        this.userId = userId;
        this.name = name;
        this.contact=contact;
        this.city=city;
        this.Country = country;
        this.description=description;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
