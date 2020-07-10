package lk.ucsc.NovelGeek.repository.book;

import lk.ucsc.NovelGeek.model.Users;
import lk.ucsc.NovelGeek.model.book.RecentlyViewed;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecentlyViewedRepository extends JpaRepository<RecentlyViewed, Long> {
    List<RecentlyViewed> findByBookId(String bookId);

    RecentlyViewed findByBookIdAndUser(String bookId, Users currentUser);

    List<RecentlyViewed> findByUser(Users currentUser, Sort date);

}
