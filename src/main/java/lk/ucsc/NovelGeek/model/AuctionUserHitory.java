package lk.ucsc.NovelGeek.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Date;

@Entity
public class AuctionUserHitory {
    @Id
    private long historyId;

    @OneToOne
    private Users bidUser;
    private Date timestamp;
    private double bidAmount;

    public AuctionUserHitory() {
    }

    public AuctionUserHitory(Users user, double bidAmount) {
        this.bidUser = user;
        this.timestamp = new Date();
        this.bidAmount = bidAmount;
    }

    public long getHistoryId() {
        return historyId;
    }

    public void setHistoryId(long historyId) {
        this.historyId = historyId;
    }

    public Users getBidUser() {
        return bidUser;
    }

    public void setBidUser(Users bidUser) {
        this.bidUser = bidUser;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public double getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(double bidAmount) {
        this.bidAmount = bidAmount;
    }
}
