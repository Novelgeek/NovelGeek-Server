package lk.ucsc.NovelGeek.repository.group;

import lk.ucsc.NovelGeek.model.group.GroupPosts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupPostRepository extends JpaRepository<GroupPosts, Long> {

}
