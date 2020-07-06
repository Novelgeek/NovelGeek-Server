package lk.ucsc.NovelGeek.repository;

import lk.ucsc.NovelGeek.model.Auction;
import org.springframework.data.repository.CrudRepository;

public interface AuctionRepository extends CrudRepository<Auction,Long> {
}
