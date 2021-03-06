package lk.ucsc.NovelGeek.repository.group;

import lk.ucsc.NovelGeek.enums.MemberStatus;
import lk.ucsc.NovelGeek.model.group.Group;
import lk.ucsc.NovelGeek.model.group.Members;
import lk.ucsc.NovelGeek.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Members, Long> {
    List<Members> findByGroup(Group group);

    List<Members> findByUsers(Optional<Users> userId);

    List<Members> findByUsersAndGroup(Optional<Users> userId, Optional<Group> group);

    List<Members> findByUsersAndMemberStatus(Optional<Users> userId, MemberStatus memberStatus);

    List<Members> findByGroupAndUsersAndMemberStatus(Optional<Group> group, Users user, MemberStatus memberStatus);

    List<Members> findByGroupAndMemberStatus(Group byId, MemberStatus requested);
}
