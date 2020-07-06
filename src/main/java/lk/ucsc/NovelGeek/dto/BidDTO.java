package lk.ucsc.NovelGeek.dto;

import org.springframework.web.bind.annotation.RequestParam;

public class BidDTO {
    private String newBid;
    private long auctionId;
    private long userId;

    public BidDTO() {
    }

    public BidDTO(String newBid, long auctionId, long userId) {
        this.newBid = newBid;
        this.auctionId = auctionId;
        this.userId = userId;
    }

    public String getNewBid() {
        return newBid;
    }

    public void setNewBid(String newBid) {
        this.newBid = newBid;
    }

    public long getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(long auctionId) {
        this.auctionId = auctionId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
