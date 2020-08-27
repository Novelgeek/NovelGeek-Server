package lk.ucsc.NovelGeek.repository;

import lk.ucsc.NovelGeek.model.Posts;
import lk.ucsc.NovelGeek.model.PostsReports;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostReportRepository extends CrudRepository<PostsReports, Long> {

    List<PostsReports> findByPosts(Posts post);

    @Query(value="SELECT DISTINCT postid FROM posts_reports",nativeQuery = true)
    public List<Long> findUniquePosts();

    @Query(value="SELECT COUNT(r.reportid) FROM posts_reports r WHERE r.postid =?1 AND r.id =?2",nativeQuery = true)
    public long checkIsReported(long postid, long id);

    @Query(value="SELECT r.reportid FROM posts_reports r WHERE r.postid =?1 AND r.id =?2",nativeQuery = true)
    public long getEntry(long postid, long id);

    @Query(value = "SELECT COUNT(r.reportid) FROM posts_reports r WHERE r.postid =?1", nativeQuery = true)
    public long countReports(long id);
}
