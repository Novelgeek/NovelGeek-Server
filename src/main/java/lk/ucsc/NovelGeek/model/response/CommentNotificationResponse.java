package lk.ucsc.NovelGeek.model.response;

public class CommentNotificationResponse {
    private long postid;
    private long notiid;
    //private long commentid;
    private String commentor;
    private String postTitle;

    public long getPostid() { return postid; }
    public void setPostid(long postid) { this.postid = postid; }

    public long getNotiid() { return notiid; }
    public void setNotiid(long notiid) { this.notiid = notiid; }

    //public long getCommentid() { return commentid; }
    //public void setCommentid(long commentid) { this.commentid = commentid; }

    public String getCommentor() { return commentor; }
    public void setCommentor(String commentor) { this.commentor = commentor; }

    public String getPostTitle() { return postTitle; }
    public void setPostTitle(String postTitle) { this.postTitle = postTitle; }
}
