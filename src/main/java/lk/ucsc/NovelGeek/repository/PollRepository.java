package lk.ucsc.NovelGeek.repository;

import lk.ucsc.NovelGeek.model.Poll;
import lk.ucsc.NovelGeek.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PollRepository extends JpaRepository <Poll, Long>{


}
