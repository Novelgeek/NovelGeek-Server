package lk.ucsc.NovelGeek.model.response;

import java.util.Date;

public class PostResponse {

    private long postid;
    private String username;
    private String title;
    private Date publishedDate;
    private String imagePath;
    private String description;
    private String sharedtype;
    private long likecount;
    private long commentcount;
    private boolean isOwned;
    private boolean isLiked;

    public long getPostid() { return postid; }

    public String getTitle() { return title; }

    public Date getPublishedDate() { return publishedDate; }

    public String getImagePath() { return imagePath; }

    public String getDescription() { return description; }

    public String getSharedtype() { return sharedtype; }

    public long getLikecount() { return likecount; }

    public boolean isOwned() { return isOwned; }

    public boolean isLiked() { return isLiked; }

    public long getCommentcount() { return commentcount; }

    public String getUsername() { return username; }

    public void setPostid(long postid) { this.postid = postid; }

    public void setTitle(String title) { this.title = title; }

    public void setPublishedDate(Date publishedDate) { this.publishedDate = publishedDate; }

    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public void setDescription(String description) { this.description = description; }

    public void setSharedtype(String sharedtype) { this.sharedtype = sharedtype; }

    public void setLikecount(long likecount) { this.likecount = likecount; }

    public void setOwned(boolean owned) { isOwned = owned; }

    public void setLiked(boolean liked) { isLiked = liked; }

    public void setCommentcount(long commentcount) { this.commentcount = commentcount; }

    public void setUsername(String username) { this.username = username; }
}
