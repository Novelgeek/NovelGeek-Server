package lk.ucsc.NovelGeek.model.book;

import lk.ucsc.NovelGeek.model.FanFiction;
import lk.ucsc.NovelGeek.model.Users;

import javax.persistence.*;
import java.util.Date;

@Entity
public class LocalBookReview {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO )
    private long reviewId;

    @Column(columnDefinition="TEXT")
    private String review;

    @ManyToOne
    @JoinColumn(name="users_id")
    private Users userId;

    @ManyToOne
    @JoinColumn(name="id")
    private LocalBook localBook;

    private Date timestamp;

    public long getReviewId() {
        return reviewId;
    }

    public void setReviewId(long reviewId) {
        this.reviewId = reviewId;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    public LocalBook getLocalBook() {
        return localBook;
    }

    public void setLocalBook(LocalBook localBook) {
        this.localBook = localBook;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
