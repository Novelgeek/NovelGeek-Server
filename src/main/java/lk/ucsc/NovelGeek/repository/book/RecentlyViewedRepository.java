package lk.ucsc.NovelGeek.repository.book;

import lk.ucsc.NovelGeek.model.book.RecentlyViewed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecentlyViewedRepository extends JpaRepository<RecentlyViewed, Long> {
    RecentlyViewed findByBookId(String bookId);
}
