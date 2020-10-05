package lk.ucsc.NovelGeek.service;

import lk.ucsc.NovelGeek.dto.AuctionDTO;
import lk.ucsc.NovelGeek.dto.SaleDTO;
import lk.ucsc.NovelGeek.enums.AuctionStatus;
import lk.ucsc.NovelGeek.model.Auction;
import lk.ucsc.NovelGeek.model.AuctionUserHistory;
import lk.ucsc.NovelGeek.model.Users;
import lk.ucsc.NovelGeek.repository.AuctionRepository;
import lk.ucsc.NovelGeek.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

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
        List<Auction> list= (List<Auction>) auctionRepository.findAll();
        list.forEach(auction ->{
            Date finishDate = new Date(auction.getFinishDate());
            if(!calculateDateDifference(finishDate)){
                auction.setAuctionStatus(AuctionStatus.FINISHED);
            }
        });
        return list;
    }
    public Boolean calculateDateDifference(Date old){
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Date firstDate = new Date();
        Date secondDate = old;

        long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
        return diffInMillies>0;
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

    public ResponseEntity<Auction> makeSale(SaleDTO saleDTO) {
        Optional<Auction> op1 = auctionRepository.findById(saleDTO.getAuctionId());
        Optional<Users> op2 = userRepository.findById(saleDTO.getBidderId());
        if(!op1.isEmpty() && !op2.isEmpty()){
            Auction auction=op1.get();
            Users highestBidder=op2.get();

            auction.setAuctionStatus(AuctionStatus.FINISHED);
            auction.setCurrentBidUser(highestBidder);

            return new ResponseEntity<Auction>(auctionRepository.save(auction), HttpStatus.OK);

        }else{
            return new ResponseEntity<Auction>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
