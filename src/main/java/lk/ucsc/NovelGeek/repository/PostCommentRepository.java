package lk.ucsc.NovelGeek.repository;

import lk.ucsc.NovelGeek.model.Posts;
import lk.ucsc.NovelGeek.model.PostsComments;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostCommentRepository extends CrudRepository<PostsComments, Long> {

    List<PostsComments> findByPosts(Posts post);
    PostsComments findById(long id);
    @Query(value = "SELECT COUNT(c.postid) FROM posts_comments c WHERE c.postid =?1", nativeQuery = true)
    public long countComments(long id);
}

