package lk.ucsc.NovelGeek.repository;

import lk.ucsc.NovelGeek.model.BookRating;
import lk.ucsc.NovelGeek.model.Books;
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
