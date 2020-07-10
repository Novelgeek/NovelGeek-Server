package lk.ucsc.NovelGeek.repository;

import lk.ucsc.NovelGeek.model.PostsReports;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PostReportRepository extends CrudRepository<PostsReports, Long> {

    @Query(value="SELECT COUNT(r.reportid) FROM posts_reports r WHERE r.postid =?1 AND r.id =?2",nativeQuery = true)
    public long checkIsReported(long postid, long id);

    @Query(value="SELECT r.reportid FROM posts_reports r WHERE r.postid =?1 AND r.id =?2",nativeQuery = true)
    public long getEntry(long postid, long id);
}
