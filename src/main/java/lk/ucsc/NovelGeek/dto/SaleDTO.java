package lk.ucsc.NovelGeek.dto;

public class SaleDTO {
    private long auctionId;
    private long bidderId;

    public SaleDTO() {
    }

    public SaleDTO(long auctionId, long bidderId) {
        this.auctionId = auctionId;
        this.bidderId = bidderId;
    }

    public long getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(long auctionId) {
        this.auctionId = auctionId;
    }

    public long getBidderId() {
        return bidderId;
    }

    public void setBidderId(long bidderId) {
        this.bidderId = bidderId;
    }
}
