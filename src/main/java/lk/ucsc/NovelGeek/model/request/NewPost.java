package lk.ucsc.NovelGeek.model.request;

import java.util.Date;

public class NewPost {

    private String title;
    private String description;
    private String sharedtype;


    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getSharedtype() { return sharedtype; }

    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setSharedType(String sharedtype) { this.sharedtype = sharedtype; }
}
