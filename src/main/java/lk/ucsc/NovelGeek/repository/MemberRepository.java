package lk.ucsc.NovelGeek.repository;

import lk.ucsc.NovelGeek.model.Group;
import lk.ucsc.NovelGeek.model.Members;
import lk.ucsc.NovelGeek.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Members, Long> {
    List<Members> findByGroup(Optional<Group> group);

    List<Members> findByUsers(Optional<Users> userId);
}
