package lk.ucsc.NovelGeek.repository;

import lk.ucsc.NovelGeek.model.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<Option, Long> {
    void deleteByPollId(Long Id);
}
