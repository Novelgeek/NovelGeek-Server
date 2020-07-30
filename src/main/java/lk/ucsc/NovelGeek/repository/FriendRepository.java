package lk.ucsc.NovelGeek.repository;

import lk.ucsc.NovelGeek.model.Friends;
import lk.ucsc.NovelGeek.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<Friends, Long> {
    Friends findByUser1AndUser2(Users user1, Users user2);
    Friends findByUser2AndUser1(Users user2, Users user1);
    List<Friends> findByUser1AndStatus(Users user1, String status);
}
