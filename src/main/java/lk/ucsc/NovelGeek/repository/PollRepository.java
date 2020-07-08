package lk.ucsc.NovelGeek.repository;

import lk.ucsc.NovelGeek.model.Poll;
import lk.ucsc.NovelGeek.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PollRepository extends JpaRepository <Poll, Long>{

    List<Poll> findbyUser(Users user);
    public List<Poll> findByUserAndVisible(Users user, boolean b);
}
