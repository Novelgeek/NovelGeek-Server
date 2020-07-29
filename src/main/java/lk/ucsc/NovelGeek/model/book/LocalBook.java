package lk.ucsc.NovelGeek.model.book;

import lk.ucsc.NovelGeek.model.Users;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity
public class LocalBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    String title;
    String description;
    String isbn;
    int year;
    String author;
    String publisher;
    String imgUrl;
    String fileUrl;

    @ManyToOne
    Users users;

    @ManyToOne
    Genres genres;

    public LocalBook() {
    }

    public LocalBook(String title, String description, String isbn, int year, String author, String publisher, String imgUrl, String fileUrl, Users users, Genres genres) {
        this.title = title;
        this.description = description;
        this.isbn = isbn;
        this.year = year;
        this.author = author;
        this.publisher = publisher;
        this.imgUrl = imgUrl;
        this.fileUrl = fileUrl;
        this.users = users;
        this.genres = genres;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Genres getGenres() {
        return genres;
    }

    public void setGenres(Genres genres) {
        this.genres = genres;
    }
}
