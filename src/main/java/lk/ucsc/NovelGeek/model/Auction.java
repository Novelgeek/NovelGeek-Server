package lk.ucsc.NovelGeek.model;

import lk.ucsc.NovelGeek.enums.AuctionStatus;

import javax.persistence.*;
import java.util.List;

@Entity
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long auctionId;
    @ManyToOne
    @JoinColumn(name = "users_id")
    private Users user;
    private String bookTitle;
    @Column(length = 1000)
    private String bookDescription;


    private double startingBid;

    private double currentBid;

    @OneToOne
    @JoinColumn(name="currentBidUser")
    private Users currentBidUser;


    private long numberOfBids;
    private String finishDate;
    private String imageUrl;

    @OneToMany
    private List<AuctionUserHitory> auctionUserHitory;

    @Enumerated(EnumType.STRING)
    private AuctionStatus auctionStatus;

    public Auction() {
    }

    public Auction(Users user, String bookTitle, String bookDescription, double startingBid, double currentBid, long numberOfBids, String finishDate, String imageUrl) {
        this.user = user;
        this.bookTitle = bookTitle;
        this.bookDescription = bookDescription;
        this.startingBid = startingBid;
        this.currentBid = currentBid;
        this.numberOfBids = numberOfBids;
        this.finishDate = finishDate;
        this.imageUrl = imageUrl;
    }
    public Auction(Users user, String bookTitle, String bookDescription, double startingBid, String finishDate, String imageUrl) {
        this.user = user;
        this.bookTitle = bookTitle;
        this.bookDescription = bookDescription;
        this.startingBid=startingBid;
        this.finishDate = finishDate;
        this.imageUrl = imageUrl;
    }

    public List<AuctionUserHitory> getAuctionUserHitory() {
        return auctionUserHitory;
    }

    public void setAuctionUserHitory(List<AuctionUserHitory> auctionUserHitory) {
        this.auctionUserHitory = auctionUserHitory;
    }

    public AuctionStatus getAuctionStatus() {
        return auctionStatus;
    }

    public void setAuctionStatus(AuctionStatus auctionStatus) {
        this.auctionStatus = auctionStatus;
    }

    public Users getCurrentBidUser() {
        return currentBidUser;
    }

    public void setCurrentBidUser(Users currentBidUser) {
        this.currentBidUser = currentBidUser;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public long getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(long auctionId) {
        this.auctionId = auctionId;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
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

    public double getStartingBid() {
        return startingBid;
    }

    public void setStartingBid(double startingBid) {
        this.startingBid = startingBid;
    }

    public double getCurrentBid() {
        return currentBid;
    }

    public void setCurrentBid(double currentBid) {
        this.currentBid = currentBid;
    }

    public long getNumberOfBids() {
        return numberOfBids;
    }

    public void setNumberOfBids(long numberOfBids) {
        this.numberOfBids = numberOfBids;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }
}
