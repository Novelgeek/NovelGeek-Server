package lk.ucsc.NovelGeek.model.book;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class LocalBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    String title;
    String description;
    String isbn;
    String year;
    String author;
    String genres;
    String searchTerms;
    String urls;
    String publisher;
    String imgUrl;
    String fileUrl;

    public LocalBook(long id, String title, String description, String isbn, String year, String author, String genres, String searchTerms, String urls, String publisher, String imgUrl, String fileUrl) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.isbn = isbn;
        this.year = year;
        this.author = author;
        this.genres = genres;
        this.searchTerms = searchTerms;
        this.urls = urls;
        this.publisher = publisher;
        this.imgUrl = imgUrl;
        this.fileUrl = fileUrl;
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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getSearchTerms() {
        return searchTerms;
    }

    public void setSearchTerms(String searchTerms) {
        this.searchTerms = searchTerms;
    }

    public String getUrls() {
        return urls;
    }

    public void setUrls(String urls) {
        this.urls = urls;
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
}
