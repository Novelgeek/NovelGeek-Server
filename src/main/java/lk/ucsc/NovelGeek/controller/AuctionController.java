package lk.ucsc.NovelGeek.controller;

import lk.ucsc.NovelGeek.dto.AuctionDTO;
import lk.ucsc.NovelGeek.dto.BidDTO;
import lk.ucsc.NovelGeek.model.Auction;
import lk.ucsc.NovelGeek.service.AuctionService;
import lk.ucsc.NovelGeek.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("auction")
public class AuctionController {
    @Autowired
    private BookService bookService;

    @Autowired
    private AuctionService auctionService;

    @PostMapping(path="/addauction")
    @ResponseBody
    public ResponseEntity<Auction> addAuction(
            @RequestParam(value = "file",required = false) MultipartFile file,
            @RequestParam("bookTitle") String title,
            @RequestParam("startingBid") String bid,
            @RequestParam("bookDescription") String description,
            @RequestParam("finishDate") String date,
            @RequestParam("userId") String id



    ){

        return auctionService.addAuction(new AuctionDTO(file, title, description,Double.parseDouble(bid), date, Long.parseLong(id)));

    }

    @GetMapping(path = "/getauctions")
    @ResponseBody
    public List<Auction> getAuctions(){
        return auctionService.getAuctions();

    }
    @PostMapping(path="/addnewbid")
    @ResponseBody
    public Auction addNewBid(@RequestBody BidDTO bid ){
        System.out.println(bid.getUserId()+"coming");
        return auctionService.addNewBid(Double.parseDouble(bid.getNewBid()), bid.getAuctionId(), bid.getUserId());

    }
    @GetMapping(path = "/getauctiondata/{id}")
    public Auction getAuctionData(@PathVariable("id") Long aid){
        System.out.println("get auction data");
        return auctionService.getAuctionData(aid);

    }
    @GetMapping(path="/test/{id}")
    @ResponseBody
    public Auction test(){
        System.out.println("test");
        return null;

    }


}
