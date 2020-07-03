package lk.ucsc.NovelGeek.dto;

import lk.ucsc.NovelGeek.model.Comment;

import java.util.List;

public class ReviewDTO {
    private String bookId;
    private String reviewDescription;
    private long userId;


    public ReviewDTO() {
    }

    public ReviewDTO(String bookId, String reviewDescription, long userId) {
        this.bookId = bookId;
        this.reviewDescription = reviewDescription;
        this.userId = userId;

    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getReviewDescription() {
        return reviewDescription;
    }

    public void setReviewDescription(String reviewDescription) {
        this.reviewDescription = reviewDescription;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
