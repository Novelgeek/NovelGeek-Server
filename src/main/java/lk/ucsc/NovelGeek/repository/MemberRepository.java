package lk.ucsc.NovelGeek.repository;

import lk.ucsc.NovelGeek.model.Members;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Members, Long> {
    public List<Object[]> findByUsers(Long id);
}
