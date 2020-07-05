package lk.ucsc.NovelGeek.service;

import lk.ucsc.NovelGeek.dto.CommentDTO;
import lk.ucsc.NovelGeek.dto.ReviewDTO;
import lk.ucsc.NovelGeek.model.*;
import lk.ucsc.NovelGeek.model.book.BookRating;
import lk.ucsc.NovelGeek.model.book.Books;
import lk.ucsc.NovelGeek.model.book.RecentlyViewed;
import lk.ucsc.NovelGeek.model.request.RatingRequest;
import lk.ucsc.NovelGeek.repository.AuthRepository;
import lk.ucsc.NovelGeek.repository.book.BookRatingRepository;
import lk.ucsc.NovelGeek.repository.book.BookRepository;
import lk.ucsc.NovelGeek.repository.ReviewRepository;
import lk.ucsc.NovelGeek.repository.book.RecentlyViewedRepository;
import lk.ucsc.NovelGeek.service.recommendation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class BookService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private AuthRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookRatingRepository bookRatingRepository;

    @Autowired
    private CollaborativeFilter collaborativeFilter;

    @Autowired
    private RecentlyViewedRepository recentlyViewedRepository;


    //get current user
    private Users getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users currentUser = userRepository.findByEmail(auth.getName());
        return currentUser;
    }


    public List<Review> getReviews(String bookId) {
        List<Review> reviews = reviewRepository.findByBookId(bookId);
        reviews.forEach(review -> {
            List<Comment> comments=review.getComments();
            comments.forEach(comment -> {
                String comment_age=calculateDateDifference(comment.getTimestamp());
                comment.setCommentAge(comment_age);
            });
            String post_age=calculateDateDifference(review.getTimestamp());
            review.setReviewAge(post_age);
        });
        return reviews;
    }

    public Review addReview(ReviewDTO reviewDTO) {
        Review review = new Review(reviewDTO.getReviewDescription(),this.getCurrentUser(),reviewDTO.getBookId(), 0);
        return reviewRepository.save(review);
    }

    public Review addComment(CommentDTO commentDTO) {
        Optional<Review> option1=reviewRepository.findById(commentDTO.getReviewId());
        Optional<Users> option2=userRepository.findById(commentDTO.getCommentedUserId());
        if(!option1.isEmpty() && !option2.isEmpty()){
            Review review=option1.get();
            Users user=option2.get();
            Comment comment = new Comment(user,commentDTO.getCommentDescription());
            review.getComments().add(comment);
            return reviewRepository.save(review);
        }else{
            return null;
        }

    }
    public String calculateDateDifference(Date old){
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Date firstDate = new Date();
        Date secondDate = old;

        long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        if(diff<1){
            long diff2=TimeUnit.HOURS.convert(diffInMillies,TimeUnit.MILLISECONDS);
            if(diff2<1){
                long diff3=TimeUnit.MINUTES.convert(diffInMillies,TimeUnit.MILLISECONDS);
                if(diff3<1){
                    return "Just now";
                }
                return diff3==1 ? diff3+ " minute ago":diff3+" minutes ago";
            }
            return diff2==1 ? diff2+ " hour ago":diff2+" hours ago";
        }
        return diff==1 ? diff+ " day ago":diff+" days ago";
    }

    public Object addRating(RatingRequest ratingRequest) {
        Books findBook = bookRepository.findByBookId(ratingRequest.getBookId());
        Books currentBook;
        if(findBook == null) {
            Books book = new Books();
            book.setBookId(ratingRequest.getBookId());
            book.setTitle(ratingRequest.getTitle());
            book.setImg(ratingRequest.getImg());
            currentBook = bookRepository.save(book);
        } else {
            currentBook = findBook;
        }

        Users currentUser = this.getCurrentUser();
        BookRating bookRating = bookRatingRepository.findByBookAndUser(currentBook, currentUser);
        if(bookRating == null){
            BookRating newBookRating = new BookRating();
            newBookRating.setBook(currentBook);
            newBookRating.setUser(currentUser);
            newBookRating.setRating(ratingRequest.getMyRating());
            collaborativeFilter.slopeOne(currentUser);
            return bookRatingRepository.save(newBookRating);
        } else {
            bookRating.setRating(ratingRequest.getMyRating());
            bookRatingRepository.save(bookRating);
            collaborativeFilter.slopeOne(currentUser);
            return null;
        }

    }

    public Object getRecommendedBooks() {
        Users users = this.getCurrentUser();
        List<Books> recommendedBooks = collaborativeFilter.getMyRecommendations(users);

        users.getBookRatings().forEach(bookRating -> {
            if (recommendedBooks.contains(bookRating.getBook())){
                recommendedBooks.remove(bookRating.getBook());
            }
        });
        return recommendedBooks;
    }

    public Object updateView(RatingRequest ratingRequest) {
        Users users = this.getCurrentUser();

        RecentlyViewed check = recentlyViewedRepository.findByBookIdAndUser(ratingRequest.getBookId(), users);
        if ( check != null){
            check.setDate(new Date());
            recentlyViewedRepository.save(check);

            return check;
        }

        if (users.getRecentlyViewed().size() == 6) {
            recentlyViewedRepository.delete(recentlyViewedRepository.findByUser(users, Sort.by(Sort.Direction.ASC, "date")).get(0));
        }

        RecentlyViewed recentlyViewed = new RecentlyViewed();
        recentlyViewed.setBookId(ratingRequest.getBookId());
        recentlyViewed.setImg(ratingRequest.getImg());
        recentlyViewed.setTitle(ratingRequest.getTitle());
        recentlyViewed.setUser(this.getCurrentUser());
        recentlyViewed.setDate(new Date());

        return recentlyViewedRepository.save(recentlyViewed);
    }

    public Object getRecentlyViewed() {
        return recentlyViewedRepository.findByUser(this.getCurrentUser(), Sort.by(Sort.Direction.DESC, "date"));
    }

    public Object getUserRating(String bookId) {
        BookRating bookRating = bookRatingRepository.findByBookAndUser(bookRepository.findByBookId(bookId), this.getCurrentUser());
        if (bookRating == null) {
            return null;
        }
        return bookRating.getRating();
    }

    public Object getUserBookRatings(){
        return this.getCurrentUser().getBookRatings();
    }
}
