package lk.ucsc.NovelGeek.model.response;

import lk.ucsc.NovelGeek.model.Option;

import java.util.Date;
import java.util.List;

public class PollResponse {

    private Long pollid;
    private String title;

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    private List<Option> options;

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    private Date endDate;
    private String username;
    private Boolean visible;

    public Long getPollid() {
        return pollid;
    }

    public void setPollid(Long pollid) {
        this.pollid = pollid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
