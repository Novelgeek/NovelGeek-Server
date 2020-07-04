package lk.ucsc.NovelGeek.repository.book;

import lk.ucsc.NovelGeek.model.book.BookRating;
import lk.ucsc.NovelGeek.model.book.Books;
import lk.ucsc.NovelGeek.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRatingRepository extends JpaRepository<BookRating, Long> {
    List<BookRating> findByUser(Users user);
    List<BookRating> findByBook(Books book);
    BookRating findByBookAndUser(Books book, Users user);
}
