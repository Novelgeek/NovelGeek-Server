package lk.ucsc.NovelGeek.dto;

public class CommentDTO {
    private long commentedUserId;
    private long reviewId;
    private String commentDescription;


    public CommentDTO() {
    }

    public CommentDTO(long commentedUserId, long reviewId, String commentDescription) {
        this.commentedUserId = commentedUserId;
        this.reviewId = reviewId;
        this.commentDescription = commentDescription;
    }

    public long getReviewId() {
        return reviewId;
    }

    public void setReviewId(long reviewId) {
        this.reviewId = reviewId;
    }

    public long getCommentedUserId() {
        return commentedUserId;
    }

    public void setCommentedUserId(long commentedUserId) {
        this.commentedUserId = commentedUserId;
    }

    public String getCommentDescription() {
        return commentDescription;
    }

    public void setCommentDescription(String commentDescription) {
        this.commentDescription = commentDescription;
    }
}
