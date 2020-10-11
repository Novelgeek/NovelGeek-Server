package lk.ucsc.NovelGeek.repository;

import lk.ucsc.NovelGeek.model.FanFiction;

import lk.ucsc.NovelGeek.model.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface FanFictionRepository extends CrudRepository<FanFiction, Long> {

@Query(value = "SELECT * FROM fan_fiction ff WHERE ff.user_id = ?1" , nativeQuery = true)
    public List<FanFiction> getFanFictionsByuserID(long userId);
}
