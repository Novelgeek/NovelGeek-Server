package lk.ucsc.NovelGeek.service;

import lk.ucsc.NovelGeek.dto.CommentDTO;
import lk.ucsc.NovelGeek.dto.ReviewDTO;

import lk.ucsc.NovelGeek.model.Comment;
import lk.ucsc.NovelGeek.model.Review;
import lk.ucsc.NovelGeek.model.Users;

import lk.ucsc.NovelGeek.model.book.*;
import lk.ucsc.NovelGeek.model.request.RatingRequest;

import lk.ucsc.NovelGeek.repository.AuthRepository;
import lk.ucsc.NovelGeek.repository.book.*;
import lk.ucsc.NovelGeek.repository.ReviewRepository;
import lk.ucsc.NovelGeek.service.recommendation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
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

    @Autowired
    private LocalBookRepository localBookRepository;

    @Autowired
    private FeaturedBookRepository featuredBookRepository;

    @Autowired
    private LocalBookReviewRepository localBookReviewRepository;


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
        if(recommendedBooks.size() == 0) {
            recommendedBooks = collaborativeFilter.slopeOne(users);
        }
        List<Books> finalRecommendedBooks = recommendedBooks;
        users.getBookRatings().forEach(bookRating -> {
            if (finalRecommendedBooks.contains(bookRating.getBook())){
                finalRecommendedBooks.remove(bookRating.getBook());
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

        if (users.getRecentlyViewed().size() == 20) {
            recentlyViewedRepository.delete(recentlyViewedRepository.findByUser(users, Sort.by(Sort.Direction.ASC, "date")).get(0));
        }

        RecentlyViewed recentlyViewed = new RecentlyViewed();
        recentlyViewed.setBookId(ratingRequest.getBookId());
        recentlyViewed.setImg(ratingRequest.getImg());
        recentlyViewed.setTitle(ratingRequest.getTitle());
        recentlyViewed.setUser(this.getCurrentUser());
        recentlyViewed.setDate(new Date());
        if(ratingRequest.getGenre()!=null){
            recentlyViewed.setGenre(ratingRequest.getGenre());
        }


        return recentlyViewedRepository.save(recentlyViewed);
    }

    public Object getRecentlyViewed() {
        List<RecentlyViewed> list= recentlyViewedRepository.findByUser(this.getCurrentUser(), Sort.by(Sort.Direction.DESC, "date"));
        int limit;
        if (list.size() > 6 ){
            limit = 6;
        } else {
            limit = list.size();
        }
        return new ArrayList<RecentlyViewed>(list.subList(0, limit));
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

    public  Object getFriendBookRatings(String email){
        Users users = userRepository.findByEmail(email);
        return users.getBookRatings();
    }

    public Object uploadNewBook(String title, String description, String isbn, int year, String author, String genres, String publisher, String fileUrl, String imageUrl) {
        LocalBook localBook = new LocalBook(title, description, isbn, year, author, publisher, imageUrl, fileUrl, this.getCurrentUser(), genres);
        localBookRepository.save(localBook);

        return null;
    }

    public Object getLocalBooks(){
        return localBookRepository.findAll();
    }


    public Object getFeaturedBooks(){
        return featuredBookRepository.findAll();
    }

    public void boostBook(Map<String, Object> boostBookParam) {
        FeaturedBook currentBook = featuredBookRepository.findByLocalBook(localBookRepository.findById(Long.valueOf((String) boostBookParam.get("bookId"))).get());
        if(currentBook != null){
            currentBook.setFeaturedBy(this.getCurrentUser());
            if (currentBook.getFeaturedTo().isAfter(LocalDate.now())) {
                currentBook.setFeaturedTo(currentBook.getFeaturedTo().plusDays(Long.valueOf((String) boostBookParam.get("days"))));
            }else {
                currentBook.setFeaturedTo(LocalDate.now().plusDays(Long.valueOf((String) boostBookParam.get("days"))));
            }

            currentBook.setPaymentId((String) boostBookParam.get("orderId"));
            featuredBookRepository.save(currentBook);
        } else {
            FeaturedBook featuredBook = new FeaturedBook();
            featuredBook.setFeaturedBy(this.getCurrentUser());
            featuredBook.setFeaturedFrom(LocalDate.now());
            featuredBook.setFeaturedTo(LocalDate.now().plusDays(Long.valueOf((String) boostBookParam.get("days"))));
            featuredBook.setLocalBook(localBookRepository.findById(Long.valueOf((String) boostBookParam.get("bookId"))).get());
            featuredBook.setPaymentId((String) boostBookParam.get("orderId"));
            featuredBookRepository.save(featuredBook);
        }

    }

    public Object addLocalReview(ReviewDTO reviewDTO) {
        LocalBookReview localBookReview = new LocalBookReview();
        localBookReview.setLocalBook(localBookRepository.findById(Long.valueOf(reviewDTO.getBookId())).get());
        localBookReview.setReview(reviewDTO.getReviewDescription());
        localBookReview.setTimestamp(new Date());
        localBookReview.setUserId(this.getCurrentUser());
        return localBookReviewRepository.save(localBookReview);
    }

    public Object getLocalBookReviews(Long bookId) {
        return localBookReviewRepository.findByLocalBook(localBookRepository.findById(bookId).get());
    }

    public Object getLocalBook(Long bookId) {
        return localBookRepository.findById(bookId);
    }

    public void deleteLocalBook(Long bookId) {
        LocalBook localBook = localBookRepository.findById(bookId).get();
        FeaturedBook featuredBooks = featuredBookRepository.findByLocalBook(localBook);
        List<LocalBookReview> localBookReviews = localBookReviewRepository.findByLocalBook(localBook);
        if(featuredBooks != null) {
            featuredBookRepository.delete(featuredBooks);
        }
        if(localBookReviews.size() !=0){
            localBookReviewRepository.deleteAll(localBookReviews);
        }

        localBookRepository.delete(localBook);
    }
}
