package lk.ucsc.NovelGeek.repository.book;

import lk.ucsc.NovelGeek.model.book.FeaturedBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeaturedBookRepository extends JpaRepository<lk.ucsc.NovelGeek.model.book.FeaturedBook, Long> {

}
