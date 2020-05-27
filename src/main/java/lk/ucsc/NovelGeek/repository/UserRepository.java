package lk.ucsc.NovelGeek.repository;

import lk.ucsc.NovelGeek.model.UserDetails;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository  extends CrudRepository<UserDetails,Long> {
}
