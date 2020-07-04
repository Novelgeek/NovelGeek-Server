package lk.ucsc.NovelGeek.model.book;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Set;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "bookRatings",})
public class Books {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String bookId;

    private String title;

    private String img;

    @OneToMany(targetEntity = BookRating.class, mappedBy = "book")
    Set<BookRating> bookRatings;

    public Set<BookRating> getBookRatings() {
        return bookRatings;
    }

    public void setBookRatings(Set<BookRating> bookRatings) {
        this.bookRatings = bookRatings;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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
}
