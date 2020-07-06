package lk.ucsc.NovelGeek.dto;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public class AuctionDTO {
    private MultipartFile file;
    private String bookTitle;
    private String bookDescription;
    private double startingBid;
    private String finishDate;
    private long userId;

    public AuctionDTO() {
    }

    public AuctionDTO(MultipartFile file, String bookTitle, String bookDescription, double startingBid, String finishDate, long userId) {
        this.file = file;
        this.bookTitle = bookTitle;
        this.bookDescription = bookDescription;
        this.startingBid = startingBid;
        this.finishDate = finishDate;
        this.userId = userId;
    }

    public double getStartingBid() {
        return startingBid;
    }

    public void setStartingBid(double startingBid) {
        this.startingBid = startingBid;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
