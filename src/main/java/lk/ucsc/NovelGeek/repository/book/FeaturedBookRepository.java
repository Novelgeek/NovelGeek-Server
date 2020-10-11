package lk.ucsc.NovelGeek.repository.book;

import lk.ucsc.NovelGeek.model.book.FeaturedBook;
import lk.ucsc.NovelGeek.model.book.LocalBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeaturedBookRepository extends JpaRepository<FeaturedBook, Long> {
    FeaturedBook findByLocalBook(LocalBook localBook);

}
