package lk.ucsc.NovelGeek.controller;

import lk.ucsc.NovelGeek.dto.AuctionDTO;
import lk.ucsc.NovelGeek.dto.BidDTO;
import lk.ucsc.NovelGeek.dto.CommentDTO;
import lk.ucsc.NovelGeek.dto.ReviewDTO;
import lk.ucsc.NovelGeek.model.Auction;
import lk.ucsc.NovelGeek.model.Comment;
import lk.ucsc.NovelGeek.model.Review;
import lk.ucsc.NovelGeek.model.Test;

import lk.ucsc.NovelGeek.repository.AuctionRepository;
import lk.ucsc.NovelGeek.service.AWSS3Service;
import lk.ucsc.NovelGeek.service.AuctionService;

import lk.ucsc.NovelGeek.model.request.RatingRequest;

import lk.ucsc.NovelGeek.service.BookService;
import org.hibernate.annotations.common.reflection.XMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("book")
public class BookController {
    @Autowired
    private BookService bookService;

    @Autowired
    private AuctionService auctionService;

    @Autowired
    private AWSS3Service awss3Service;


    @PostMapping(path="/check")
    @ResponseBody
    public Test check(@RequestBody Test test){
        System.out.println(test.getName());
        return null;
    }

    @GetMapping(path = "/test")
    public void test(){
        System.out.println("will you approve");

    }

    @GetMapping(path = "/getreviews/{id}")
    public List<Review> getReviews(@PathVariable("id") String bookId){
        return bookService.getReviews(bookId);

    }
    @PostMapping(path="/addreview",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Review addReview(@RequestBody ReviewDTO reviewDTO){
        return bookService.addReview(reviewDTO);
    }

    @PostMapping(path="/addcomment")
    @ResponseBody
    public Review addComment(@RequestBody CommentDTO commentDTO){
        return bookService.addComment(commentDTO);

    }
//Auction====================================================================================================



    @PostMapping(path="/addauction")
    @ResponseBody
    public ResponseEntity<Auction> addAuction(
            @RequestParam(value = "file",required = false)MultipartFile file,
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

        return auctionService.addNewBid(Double.parseDouble(bid.getNewBid()), bid.getAuctionId(), bid.getUserId());

    }




    @PostMapping("/addRating")
    public Object addRating(@RequestBody RatingRequest ratingRequest){

        return bookService.addRating(ratingRequest);
    }

    @PostMapping("/updateView")
    public Object updateView(@RequestBody RatingRequest ratingRequest){

        return bookService.updateView(ratingRequest);
    }

    @GetMapping("/getRecommendations")
    public Object getRecommendations(){
        return bookService.getRecommendedBooks();
    }

    @GetMapping("/recentlyViewed")
    public Object getRecentlyViewed(){
        return bookService.getRecentlyViewed();
    }

    @GetMapping("/userRating/{bookId}")
    public Object getUserRating(@PathVariable("bookId") String bookId){
        return bookService.getUserRating(bookId);
    }

    @GetMapping("/bookRatings")
    public Object getUserBookRatings(){

        return bookService.getUserBookRatings();
    }

    @PostMapping(path="/addNewBook")
    @ResponseBody
    public ResponseEntity<Auction> addNewBook(
            @RequestParam(value = "img",required = false)MultipartFile img,
            @RequestParam(value = "pdf",required = false)MultipartFile pdf,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("isbn") String isbn,
            @RequestParam("year") int year,
            @RequestParam("author") String author,
            @RequestParam("genres") String genres,
            @RequestParam("publisher") String publisher
    ){
        String fileUrl = null;
//        if (pdf == null){
//            fileUrl = null;
//        } else {
//            fileUrl = awss3Service.uploadFile(pdf);
//        }

        String imageUrl = null;
//        if (img == null){
//            imageUrl = null;
//        } else {
//            imageUrl = awss3Service.uploadFile(img);
//        }


        bookService.uploadNewBook(title, description, isbn, year,
                                    author, genres, publisher, fileUrl, imageUrl);


        return null;

    }

    @GetMapping("/allLocal")
    public Object getLocalBooks(){

        return bookService.getLocalBooks();
    }

    @PostMapping("/boost-book")
    public Object boostLocalBook(@RequestParam Map<String,String> allRequestParams) {
        System.out.println(allRequestParams.get("merchant_id"));
        System.out.println("Came");
        return null;
    }

    @GetMapping("/boost-book")
    public Object boostLocalBook1(@RequestParam Map<String,String> allRequestParams) {
        System.out.println(allRequestParams.get("merchant_id"));
        System.out.println("Came");
        return null;
    }


}
