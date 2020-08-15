package lk.ucsc.NovelGeek.repository;

import lk.ucsc.NovelGeek.model.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionRepository extends JpaRepository<Option, Long> {

}
