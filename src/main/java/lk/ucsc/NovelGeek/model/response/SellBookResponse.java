package lk.ucsc.NovelGeek.model.response;

import java.util.Date;

public class SellBookResponse {

    private long sellingid;
    private String username;
    private String title;
    private String description;
    private Date publishedDate;
    private float price;
    private String merchantid;
    private String imagePath;
    private String telephone;
    private boolean isOwned;
    private boolean isSold;

    public long getSellingid() { return sellingid; }
    public void setSellingid(long sellingid) { this.sellingid = sellingid; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Date getPublishedDate() { return publishedDate; }
    public void setPublishedDate(Date publishedDate) { this.publishedDate = publishedDate; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public boolean isOwned() { return isOwned; }
    public void setOwned(boolean owned) { isOwned = owned; }

    public boolean isSold() { return isSold; }
    public void setSold(boolean sold) { isSold = sold; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public float getPrice() { return price; }
    public void setPrice(float price) { this.price = price; }

    public String  getMerchantid() { return merchantid; }
    public void setMerchantid(String  merchantid) { this.merchantid = merchantid; }
}
