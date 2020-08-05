package lk.ucsc.NovelGeek.repository.group;

import lk.ucsc.NovelGeek.model.group.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
}
