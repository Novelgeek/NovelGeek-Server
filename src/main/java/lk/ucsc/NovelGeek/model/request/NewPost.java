package lk.ucsc.NovelGeek.model.request;

import org.springframework.web.multipart.MultipartFile;

import javax.mail.Multipart;


public class NewPost {

    private String title;
    private String description;
    private String sharedtype;
    private String imagePath;


    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getSharedtype() { return sharedtype; }
    public String getImagePath() { return imagePath; }

    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setSharedType(String sharedtype) { this.sharedtype = sharedtype; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
}
