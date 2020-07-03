package lk.ucsc.NovelGeek.repository;

import lk.ucsc.NovelGeek.model.Review;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReviewRepository extends CrudRepository<Review,Long> {
    List<Review> findByBookId(String id);
}
