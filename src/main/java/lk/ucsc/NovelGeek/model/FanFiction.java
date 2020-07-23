package lk.ucsc.NovelGeek.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class FanFiction {

    @Id
    private long id;

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    private String imageName;
    private String title;
    private String description;
    private String fileUrl;

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
