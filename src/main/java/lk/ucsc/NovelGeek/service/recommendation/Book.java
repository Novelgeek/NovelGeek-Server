package lk.ucsc.NovelGeek.service.recommendation;

public class Book {
    private String itemName;

    public String getItemName() {
        return itemName;
    }

    public Book(String itemName) {
        this.itemName = itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
