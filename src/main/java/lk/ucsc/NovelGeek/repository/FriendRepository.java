package lk.ucsc.NovelGeek.repository;

import lk.ucsc.NovelGeek.model.Friends;
import lk.ucsc.NovelGeek.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRepository extends JpaRepository<Friends, Long> {

}
