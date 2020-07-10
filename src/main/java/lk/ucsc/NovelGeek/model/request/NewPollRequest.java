package lk.ucsc.NovelGeek.model.request;

import io.jsonwebtoken.lang.Strings;
import lk.ucsc.NovelGeek.model.Option;

import java.util.Date;
import java.util.List;

public class NewPollRequest {

    private String title;
    private String endDate;
    private List<String> options;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }
}
