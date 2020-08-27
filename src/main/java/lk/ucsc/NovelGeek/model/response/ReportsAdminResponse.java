package lk.ucsc.NovelGeek.model.response;

public class ReportsAdminResponse {

    private long id;
    private long postid;
    private String username;
    private String userimg;
    private long reportcount;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getPostid() { return postid; }
    public void setPostid(long postid) { this.postid = postid; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getUserimg() { return userimg; }
    public void setUserimg(String userimg) { this.userimg = userimg; }

    public long getReportcount() { return reportcount; }
    public void setReportcount(long reportcount) { this.reportcount = reportcount; }
}
