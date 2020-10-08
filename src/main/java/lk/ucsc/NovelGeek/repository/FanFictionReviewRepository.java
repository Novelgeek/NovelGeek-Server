package lk.ucsc.NovelGeek.repository;

import lk.ucsc.NovelGeek.model.FanFiction;
import lk.ucsc.NovelGeek.model.FanFictionReview;
import lk.ucsc.NovelGeek.model.Review;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FanFictionReviewRepository extends CrudRepository<FanFictionReview, Long> {
    List<FanFictionReview> findByFanFictionId(FanFiction id);
}
