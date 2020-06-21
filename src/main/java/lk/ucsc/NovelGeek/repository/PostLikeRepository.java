package lk.ucsc.NovelGeek.repository;

import lk.ucsc.NovelGeek.model.Posts;
import lk.ucsc.NovelGeek.model.PostsLikes;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PostLikeRepository extends CrudRepository<PostsLikes, Long> {

    PostsLikes findByPosts(Posts post);

    @Query(value = "SELECT COUNT(l.postid) FROM posts_likes l WHERE l.postid =?1", nativeQuery = true)
    public long countLikes(long id);

    @Query(value="SELECT COUNT(l.columnid) FROM posts_likes l WHERE l.postid =?1 AND l.id =?2",nativeQuery = true)
    public long checkIsLiked(long postid, long id);
}
