package lk.ucsc.NovelGeek.model.book;

import lk.ucsc.NovelGeek.model.Users;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class FeaturedBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long featuredId;

    private LocalDate featuredFrom;
    private LocalDate featuredTo;

    @ManyToOne
    LocalBook localBook;

    @ManyToOne
    Users featuredBy;

    private String paymentId;

    public long getFeaturedId() {
        return featuredId;
    }

    public void setFeaturedId(long featuredId) {
        this.featuredId = featuredId;
    }

    public LocalDate getFeaturedFrom() {
        return featuredFrom;
    }

    public void setFeaturedFrom(LocalDate featuredFrom) {
        this.featuredFrom = featuredFrom;
    }

    public LocalDate getFeaturedTo() {
        return featuredTo;
    }

    public void setFeaturedTo(LocalDate featuredTo) {
        this.featuredTo = featuredTo;
    }

    public LocalBook getLocalBook() {
        return localBook;
    }

    public void setLocalBook(LocalBook localBook) {
        this.localBook = localBook;
    }

    public Users getFeaturedBy() {
        return featuredBy;
    }

    public void setFeaturedBy(Users featuredBy) {
        this.featuredBy = featuredBy;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }
}
