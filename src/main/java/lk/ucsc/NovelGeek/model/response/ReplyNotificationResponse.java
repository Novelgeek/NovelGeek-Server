package lk.ucsc.NovelGeek.model.response;

public class ReplyNotificationResponse {

    private long postid;
    //private long replyid;
    private String replier;
    private String postTitle;

    public long getPostid() { return postid; }
    public void setPostid(long postid) { this.postid = postid; }

    //public long getReplyid() { return replyid; }
    //public void setReplyid(long replyid) { this.replyid = replyid; }

    public String getReplier() { return replier; }
    public void setReplier(String replier) { this.replier = replier; }

    public String getPostTitle() { return postTitle; }
    public void setPostTitle(String postTitle) { this.postTitle = postTitle; }
}
