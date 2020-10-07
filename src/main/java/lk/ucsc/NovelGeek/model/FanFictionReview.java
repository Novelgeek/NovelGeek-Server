package lk.ucsc.NovelGeek.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class FanFictionReview {
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
    private FanFiction fanFictionId;

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

    public FanFiction getFanFictionId() {
        return fanFictionId;
    }

    public void setFanFictionId(FanFiction fanFictionId) {
        this.fanFictionId = fanFictionId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
