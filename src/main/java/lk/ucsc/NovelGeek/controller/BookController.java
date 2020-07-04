package lk.ucsc.NovelGeek.controller;

import lk.ucsc.NovelGeek.dto.CommentDTO;
import lk.ucsc.NovelGeek.dto.ReviewDTO;
import lk.ucsc.NovelGeek.model.Comment;
import lk.ucsc.NovelGeek.model.Review;
import lk.ucsc.NovelGeek.model.Test;
import lk.ucsc.NovelGeek.model.request.RatingRequest;
import lk.ucsc.NovelGeek.service.BookService;
import org.hibernate.annotations.common.reflection.XMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("book")
public class BookController {
    @Autowired
    private BookService bookService;

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

    @PostMapping("/addRating")
    public Object addRating(@RequestBody RatingRequest ratingRequest){

        return bookService.addRating(ratingRequest);
    }

}
