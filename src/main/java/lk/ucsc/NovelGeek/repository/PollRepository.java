package lk.ucsc.NovelGeek.repository;

import lk.ucsc.NovelGeek.model.Poll;
import lk.ucsc.NovelGeek.model.Users;
import lk.ucsc.NovelGeek.model.response.PollResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PollRepository extends JpaRepository <Poll, Long>{

    List<Poll> findByUsers(Users byId);
}
