package lk.ucsc.NovelGeek.model.response;

public class PostReportsData {

    private long id;
    private long reportid;
    private String username;
    private String userimg;
    private String reason;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getReportid() { return reportid; }
    public void setReportid(long reportid) { this.reportid = reportid; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getUserimg() { return userimg; }
    public void setUserimg(String userimg) { this.userimg = userimg; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}
