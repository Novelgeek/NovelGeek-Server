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

@RestController
@RequestMapping("book")
public class BookController {
    @Autowired
    private BookService bookService;

    @Autowired
    private AuctionService auctionService;


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


//==================================================================================================

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


}
