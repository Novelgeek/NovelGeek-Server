package lk.ucsc.NovelGeek.model.response;

import java.util.Date;

public class PostAdminResponse {
    private long postid;
    private String username;
    private String userimg;
    private String title;
    private Date publishedDate;
    private String imagePath;
    private String description;
    private long likecount;
    private long commentcount;

    public long getPostid() { return postid; }
    public void setPostid(long postid) { this.postid = postid; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getUserimg() { return userimg; }
    public void setUserimg(String userimg) { this.userimg = userimg; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Date getPublishedDate() { return publishedDate; }
    public void setPublishedDate(Date publishedDate) { this.publishedDate = publishedDate; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public long getLikecount() { return likecount; }
    public void setLikecount(long likecount) { this.likecount = likecount; }

    public long getCommentcount() { return commentcount; }
    public void setCommentcount(long commentcount) { this.commentcount = commentcount; }
}
