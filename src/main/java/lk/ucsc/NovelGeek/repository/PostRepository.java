package lk.ucsc.NovelGeek.repository;

import lk.ucsc.NovelGeek.model.Posts;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository  extends CrudRepository<Posts,Long> {

    List<Posts> findAll();
    Posts findById(long id);
    List<Posts> findBySharedtype(String sharedtype);
}
