package lk.ucsc.NovelGeek.model.request;

public class NewSelling {

    private String title;
    private String description;
    private String imagePath;
    private String telephone;
    private String merchantid;
    private float price;

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getImagePath() { return imagePath; }
    public String getTelephone() { return telephone; }
    public String getMerchantid() { return merchantid; }
    public float getPrice() { return price; }

    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    public void setMerchantid(String merchantid) { this.merchantid = merchantid; }
    public void setPrice(float price) { this.price = price; }
}


