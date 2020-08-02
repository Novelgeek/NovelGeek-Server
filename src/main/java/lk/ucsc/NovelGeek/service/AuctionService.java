package lk.ucsc.NovelGeek.service;

import lk.ucsc.NovelGeek.dto.AuctionDTO;
import lk.ucsc.NovelGeek.enums.AuctionStatus;
import lk.ucsc.NovelGeek.model.Auction;
import lk.ucsc.NovelGeek.model.AuctionUserHistory;
import lk.ucsc.NovelGeek.model.Users;
import lk.ucsc.NovelGeek.repository.AuctionRepository;
import lk.ucsc.NovelGeek.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuctionService {
    @Autowired
    private AuctionRepository auctionRepository;
    @Autowired
    private AuthRepository userRepository;
    @Autowired
    private AWSS3Service imageService;
    public ResponseEntity<Auction> addAuction(AuctionDTO auctionDTO) {
        Optional<Users> op = userRepository.findById(auctionDTO.getUserId());
        String imageUrl="";
        if(!op.isEmpty()){
            if (auctionDTO.getFile() == null){
                imageUrl = null;
            } else {
                imageUrl = imageService.uploadFile(auctionDTO.getFile());
            }
            Users user=op.get();
            System.out.print("starting bid : ");
            Auction auction = new Auction(user, auctionDTO.getBookTitle(), auctionDTO.getBookDescription(), auctionDTO.getStartingBid(), auctionDTO.getFinishDate(), imageUrl);
            auction.setStartingBid(auctionDTO.getStartingBid());
            auction.setAuctionStatus(AuctionStatus.ONGOING);
            System.out.println(auction.getStartingBid());
            return ResponseEntity.ok(auctionRepository.save(auction));

        }else{
            return (ResponseEntity<Auction>) ResponseEntity.badRequest();
        }

    }

    public List<Auction> getAuctions() {
        return (List<Auction>) auctionRepository.findAll();
    }

    public Auction addNewBid(double bid, long auctionId, long userId) {
        Optional<Auction> op1 = auctionRepository.findById(auctionId);
        Optional<Users> op2  = userRepository.findById(userId);

        if(!op1.isEmpty() && !op2.isEmpty()){
            System.out.println("auction and user exists");
            Auction auction=op1.get();
            Users biddingUser = op2.get();
            auction.setCurrentBid(bid);
            auction.setNumberOfBids(auction.getNumberOfBids()+1);
            auction.setCurrentBidUser(biddingUser);

            AuctionUserHistory auctionUserHistory = new AuctionUserHistory(biddingUser, bid);
            auction.getAuctionUserHitory().add(auctionUserHistory);

            return auctionRepository.save(auction);
        }else{
            return null;
        }
    }

    public Auction getAuctionData(Long aid) {
        return auctionRepository.findById(aid).get();
    }
}
