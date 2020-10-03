package lk.ucsc.NovelGeek.model.response;

public class CommentReplyResponse {
    private long replyid;
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

    public long getReplyid() { return replyid; }
    public void setReplyid(long replyid) { this.replyid = replyid; }

    public boolean isOwned() { return isOwned; }
    public void setOwned(boolean owned) { isOwned = owned; }
}
