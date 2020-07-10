package lk.ucsc.NovelGeek.model.book;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lk.ucsc.NovelGeek.model.Users;

import javax.persistence.*;
import java.util.Date;

@Entity
@JsonIgnoreProperties({"user"})
public class RecentlyViewed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String bookId;

    private String title;

    @Column(length = 3000)
    private String img;

    private Date date;

    @ManyToOne
    @JoinColumn(name = "user")
    Users user;

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
