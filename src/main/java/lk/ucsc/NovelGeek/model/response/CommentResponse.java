package lk.ucsc.NovelGeek.model.response;

public class CommentResponse {
    private long commentid;
    private String username;
    private String imagePath;
    private String comment;
    private boolean isOwned;

    public String getUsername() { return username; }

    public String getImagePath() { return imagePath; }

    public String getComment() { return comment; }

    public void setUsername(String username) { this.username = username; }

    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public void setComment(String comment) { this.comment = comment; }

    public long getCommentid() { return commentid; }

    public void setCommentid(long commentid) { this.commentid = commentid; }

    public boolean isOwned() { return isOwned; }
    public void setOwned(boolean owned) { isOwned = owned; }
}
