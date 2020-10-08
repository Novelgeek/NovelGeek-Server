package lk.ucsc.NovelGeek.repository.book;

import lk.ucsc.NovelGeek.model.FanFictionReview;
import lk.ucsc.NovelGeek.model.Review;
import lk.ucsc.NovelGeek.model.book.LocalBook;
import lk.ucsc.NovelGeek.model.book.LocalBookReview;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LocalBookReviewRepository extends CrudRepository<LocalBookReview, Long> {
    List<LocalBookReview> findByLocalBook(LocalBook book);
}
